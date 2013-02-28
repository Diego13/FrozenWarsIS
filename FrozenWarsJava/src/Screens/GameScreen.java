package Screens;
import java.util.ArrayList;
import java.util.List;

import Application.Assets;
import Application.MatchManager;
import Application.MatchManager.Direction;
import GameLogic.Harpoon;
import GameLogic.Map.TypeSquare;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;


public class GameScreen implements Screen{
	private Game game;
	private OrthographicCamera guiCam;
	private SpriteBatch batcher;
	private Vector3 touchPoint;
	private Vector3 touchPoint2;
	private BoundingBox fdBounds;
	private BoundingBox fiBounds;
	private BoundingBox farBounds;
	private BoundingBox fabBounds;
	private BoundingBox harpoonBounds;
	private PenguinAnimation[] penguinAnimations;
    private float stateTime;
	private TextureRegion currentFrame;
	private MatchManager manager;
	private List<Harpoon> harpoonList;
	private String name;
	private BitmapFont font;
	private int numPlayer;

	
	public GameScreen(Game game, MatchManager manager){
		this.game=game;
		this.name = manager.getMyNamePlayer();
		numPlayer=manager.getMyIdPlayer();
		this.manager = manager;
		this.font = new BitmapFont();
		this.harpoonList = new ArrayList<Harpoon>();
		guiCam = new OrthographicCamera(21,13);
		guiCam.position.set(21f/2,13f/2,0);
		batcher = new SpriteBatch();
		touchPoint = new Vector3();
		touchPoint2 = new Vector3();
		fdBounds= new BoundingBox(new Vector3(4.75f,2.5f,0), new Vector3(7.25f,4.5f,0));
		fiBounds= new BoundingBox(new Vector3(0.25f,2.5f,0), new Vector3(2.75f,4.5f,0));
		farBounds= new BoundingBox(new Vector3(2.75f,4.6f,0), new Vector3(4.75f,7,0));
		fabBounds= new BoundingBox(new Vector3(2.75f,0,0), new Vector3(4.75f,2.4f,0));

		harpoonBounds= new BoundingBox(new Vector3(19,0,0), new Vector3(21,4,0));
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		//--------------------------------------------------------------------
		penguinAnimations = new PenguinAnimation[4];
		penguinAnimations[0] = new PenguinAnimation(Gdx.files.internal("data/pClarosRojo.png"),manager.getPlayerPosition(0),manager.getPlayerDirection(0));
		penguinAnimations[1] = new PenguinAnimation(Gdx.files.internal("data/pClarosVerde.png"),manager.getPlayerPosition(1),manager.getPlayerDirection(1));
		penguinAnimations[2] = new PenguinAnimation(Gdx.files.internal("data/pClarosAmarillo.png"),manager.getPlayerPosition(2),manager.getPlayerDirection(2));
		penguinAnimations[3] = new PenguinAnimation(Gdx.files.internal("data/pClarosAzul.png"),manager.getPlayerPosition(3),manager.getPlayerDirection(3));
        stateTime = 0f;     

      //--------------------------------------------------------------
}
	@Override
	public void dispose() {
		batcher.dispose();
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void render(float delta) {
		Vector3 position;
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();  
		batcher.setProjectionMatrix(guiCam.combined);
		batcher.enableBlending();
		batcher.begin();
		batcher.draw(Assets.getBackground(),0,0,21,13);
		
		batcher.draw(Assets.getHorizontalBarDown(),8,0,11,1);
		batcher.draw(Assets.getHorizontalBarUP(),8,12,11,1);
		batcher.draw(Assets.getVerticalBarLeft(),7,0,1,13);
		batcher.draw(Assets.getVerticalBarRigth(),19,0,1,13);
		
		
		for(int i=0;i<11;i++){
			for (int j=0;j<11;j++){
				TextureRegion texture= null;
				TypeSquare type = manager.getSquare(i,j);
				texture = Assets.getBox(); 
				batcher.draw(texture,i+8,j+1,1,1);
				if (!type.equals(TypeSquare.empty)){
					if (type.equals(TypeSquare.unbreakable)) texture = Assets.getIgloo();
					else if (type.equals(TypeSquare.Harpoon)) texture = Assets.getHarpoon();				
					else if (type.equals(TypeSquare.breakable)) texture  = Assets.getBarrel();
					else if (type.equals(TypeSquare.fissureC)) texture  = Assets.getFissureCenter();
					else if (type.equals(TypeSquare.fissureSX)) texture  = Assets.getFissureSideX();
					else if (type.equals(TypeSquare.fissureSY)) texture  = Assets.getFissureSideY();
					else if (type.equals(TypeSquare.water1SOpN)) texture  = Assets.getWater1SideOpenN();
					else if (type.equals(TypeSquare.water1SOpS)) texture  = Assets.getWater1SideOpenS();
					else if (type.equals(TypeSquare.water1SOpE)) texture  = Assets.getWater1SideOpenE();
					else if (type.equals(TypeSquare.water1SOpW)) texture  = Assets.getWater1SideOpenW();
					else if (type.equals(TypeSquare.water2SOpCornerNW)) texture  = Assets.getWater2SideOpenCornerNW();
					else if (type.equals(TypeSquare.water2SOpCornerSE)) texture  = Assets.getWater2SideOpenCornerSE();
					else if (type.equals(TypeSquare.water2SOpCornerWS)) texture  = Assets.getWater2SideOpenCornerWS();
					else if (type.equals(TypeSquare.water2SOpCornerEN)) texture  = Assets.getWater2SideOpenCornerEN();
					else if (type.equals(TypeSquare.water2SOpBridgeX)) texture  = Assets.getWater2SideOpenBridgeX();
					else if (type.equals(TypeSquare.water2SOpBridgeY)) texture  = Assets.getWater2SideOpenBridgeY();
					else if (type.equals(TypeSquare.water3SOpN)) texture  = Assets.getWater3SideNOpen();
					else if (type.equals(TypeSquare.water3SOpS)) texture  = Assets.getWater3SideSOpen();
					else if (type.equals(TypeSquare.water3SOpE)) texture  = Assets.getWater3SideEOpen();
					else if (type.equals(TypeSquare.water3SOpW)) texture  = Assets.getWater3SideWOpen();
					else if (type.equals(TypeSquare.water4SOp)) texture  = Assets.getWater4SideOpen();
					
					 batcher.draw(texture,i+8,j+1,1,1);
				}				
			}
		}	
		
		batcher.draw(Assets.getDirectionPanel(),0.25f,0,7,7);
		batcher.draw(Assets.getButtonHarpoon(),19,0,2,4);
		
		for (int i=0;i<4;i++){
			position = penguinAnimations[i].getPosition();
			batcher.draw(penguinAnimations[i].getCurrentFrame(),(position.x)+8f,(position.y+1),1,1);
		}
		
		paintPlayer();
		paintLifes();
		
		
		
		batcher.end();
		guiCam.update();
		if (Gdx.input.isTouched()){
			if (Gdx.input.isTouched(0))guiCam.unproject(touchPoint.set(Gdx.input.getX(0),Gdx.input.getY(0),0));
			if (Gdx.input.isTouched(1))guiCam.unproject(touchPoint2.set(Gdx.input.getX(1),Gdx.input.getY(1),0));
			if (fdBounds.contains(touchPoint)||fdBounds.contains(touchPoint2)){

				manager.movePlayer(Direction.right);      
			}
			else if (fiBounds.contains(touchPoint)||fiBounds.contains(touchPoint2)){
				manager.movePlayer(Direction.left);
			}
			else if (farBounds.contains(touchPoint)||farBounds.contains(touchPoint2)){
				manager.movePlayer(Direction.up);
			}
			else if (fabBounds.contains(touchPoint)||fabBounds.contains(touchPoint2)){
				manager.movePlayer(Direction.down);
			}
			
			if (harpoonBounds.contains(touchPoint)||harpoonBounds.contains(touchPoint2)){
				manager.putLance();
			}
		}
		
	}
	private void paintPlayer(){
			/*font.setColor(255,255,255,1);
			float textWidth = font.getBounds(name).width;
			float textHeight = font.getBounds(name).height;
			font.draw(batcher,name,1-textWidth/2,14-textHeight/2);*/
		if (numPlayer==0){
			batcher.draw(Assets.getLifeIconRed(),6,12,0.75f,0.75f);
		}else if(numPlayer==1){
			batcher.draw(Assets.getLifeIconGreen(),6,12,0.75f,0.75f);
		}else if(numPlayer==2){
			batcher.draw(Assets.getLifeIconYellow(),6,12,0.75f,0.75f);
		}else if(numPlayer==3){
			batcher.draw(Assets.getLifeIconBlue(),6,12,0.75f,0.75f);
		}
		
			
		
			}
			
	private void paintLifes() {
			for (int i=0;i<manager.getPlayerLifes(0);i++){
				batcher.draw(Assets.getLifeIconRed(),1+2*i,10.805f,0.75f,0.75f);
			}
			for (int i=0;i<manager.getPlayerLifes(1);i++){		
				batcher.draw(Assets.getLifeIconGreen(),1+2*i,9.68f,0.75f,0.75f);
			}
			for (int i=0;i<manager.getPlayerLifes(2);i++){
				batcher.draw(Assets.getLifeIconYellow(),1+2*i,8.55f,0.75f,0.75f);
			}
			for (int i=0;i<manager.getPlayerLifes(3);i++){
				batcher.draw(Assets.getLifeIconBlue(),1+2*i,7.43f,0.75f,0.75f);
			}

	}
	
	public void movePlayer(Direction dir, int playerId, Vector3 position) {                                     
        if (dir.equals(Direction.right)) currentFrame = penguinAnimations[playerId].getwalkAnimationR().getKeyFrame(stateTime, true); 
        else if (dir.equals(Direction.left)) currentFrame = penguinAnimations[playerId].getwalkAnimationL().getKeyFrame(stateTime, true);
        else if (dir.equals(Direction.up)) currentFrame = penguinAnimations[playerId].getwalkAnimationU().getKeyFrame(stateTime, true);
        else if (dir.equals(Direction.down)) currentFrame = penguinAnimations[playerId].getwalkAnimationD().getKeyFrame(stateTime, true);
        penguinAnimations[playerId].setPosition(position);
        penguinAnimations[playerId].setlookAt(dir);
        penguinAnimations[playerId].setCurrentFrame(currentFrame);     
	}
	
	public void putLanceAt(int xLancePosition, int yLancePosition) {
		Harpoon lance= new Harpoon(xLancePosition,yLancePosition);
		harpoonList.add(lance);		
	}

	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public MatchManager getManager() {
		return manager;
	}
	public void setManager(MatchManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	public PenguinAnimation[] getPreguinAnimations() {
		return penguinAnimations;
	}
	public void setPreguinAnimations(PenguinAnimation[] preguinAnimations) {
		this.penguinAnimations = preguinAnimations;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BitmapFont getFont() {
		return font;
	}
	public void setFont(BitmapFont font) {
		this.font = font;
	}


}