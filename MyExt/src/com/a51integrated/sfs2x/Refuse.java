package com.a51integrated.sfs2x;

import java.util.HashMap;
import java.util.Vector;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;

public class Refuse extends BaseClientRequestHandler {

	@Override
	public void handleClientRequest(User player, ISFSObject params) {
		
		MyExt parentEx = (MyExt) getParentExtension(); 
		HashMap<String,InvitationRoom> gamesInCreation = parentEx.getGamesInCreation();
		HashMap<String,User> users = parentEx.getUsers();
		String player2 = params.getUtfString("Inviter"); //Gets the name of the player who invited you.
		
		gamesInCreation.get(player2).putRefused(player.getName());
		parentEx.setGamesInCreation(gamesInCreation);

		ISFSArray refused = new SFSArray();
		Vector<String> refusedPlayers = gamesInCreation.get(player2).getRefusedPlayers();
		for(int i=0; i<refusedPlayers.size();i++){
				refused.addUtfString(refusedPlayers.get(i)); //Adds the player who refused the invitation
		}
		
		ISFSArray waiting = new SFSArray();
		Vector<String> waitingPlayers = gamesInCreation.get(player2).getWaitingPlayers();
		for(int i=0; i<waitingPlayers.size();i++){
				waiting.addUtfString(waitingPlayers.get(i)); //Adds the players who are waiting.
		}
		
        ISFSObject rtn = new SFSObject();
        rtn.putSFSArray("refusedPlayers", refused);
        rtn.putSFSArray("waitingPlayers", waiting);
       
        Vector<String> accplayer = gamesInCreation.get(player2).getAcceptedPlayers();
        for (int j=0; j<accplayer.size();j++){ //Sends the response to the joinned players.
        	parentEx.send("RefusedWaiting", rtn, users.get(accplayer.get(j)));
        }
		
        
	}


}
