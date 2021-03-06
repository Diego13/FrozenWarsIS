package com.a51integrated.sfs2x;

import java.util.Queue;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class Put2Handler extends BaseClientRequestHandler {

	@Override
	public void handleClientRequest(User player, ISFSObject params) { // a groups of 2 want to join a game
		
		MyExt parentEx = (MyExt) getParentExtension();
		Queue<User[]> queue2 = parentEx.getQueue2();
		User[] aux= new User[2];// we set in the array the group of 2 players that fired this handler
		aux[0]=player;
		aux[1]=parentEx.getUsers().get(params.getUtfString("pfriend1"));
		queue2.add(aux);// add the array to the queue for groups of 2
		
		Queue<User> queue1=parentEx.getQueue1();
		if(queue2.size()==1 && queue1.size()>=2){ //in the next ifs we see if with this players we can create a room
			ISFSObject rtn = new SFSObject();
			rtn.putUtfString("res", "He entrado por 1 de 2 y 2 de 1");
			this.send("meter1", rtn, player);
			User[] aux2= queue2.poll();
			parentEx.creatGame(queue1.poll(), queue1.poll(), aux2[0], aux2[1]);// call to the method of creating a room
			parentEx.setQueue1(queue1);  // update the queue for groups of single players
		}
		
		if(queue2.size()== 2){
			ISFSObject rtn = new SFSObject();
			rtn.putUtfString("res", "He entrado por 2 de 2");
			this.send("meter1", rtn, player);
			User[] aux2= queue2.poll();
			User[] aux3= queue2.poll();
			parentEx.creatGame(aux2[0], aux2[1], aux3[0], aux3[1]); // call to the method of creating a room
		}
		parentEx.setQueue2(queue2); // update the queue for groups of 2
	}

}