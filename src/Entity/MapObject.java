package Entity;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import Pascal.GamePanel;
import TileMap.Tile;
import TileMap.TileMap;
import Util.PColor;
import Util.PInteger;

public abstract class MapObject {

	// map
	private TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	//position and vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	//dimension
	protected int width;
	protected int height;
	
	//collision box
	protected int cwidth;
	protected int cheight;
	
	//collision
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	// animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight;
	
	// movement
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	public boolean jumping;
	public boolean falling;
	protected boolean inwater;
	
	// states
	public boolean stillX;
	public boolean stillY;
	public boolean noclip;
	public boolean flying;
	public boolean swimming;
	
	// properties
	public boolean essential;
	public boolean invincible;
	public boolean invisible;
	
	// movement attributes
	public double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;
	
	// list of mapobjects
	public static ArrayList<MapObject> entities = new ArrayList<MapObject>();
	
	//constructor
	public MapObject(TileMap tm) {
		setTileMap(tm);
		tileSize = tm.getTileSize();
	}
	
	public  boolean intersects(MapObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}
	public Rectangle getRectangle() {
		return new Rectangle((int)x - cwidth, (int)y - cheight, cwidth, cheight);
	}
	public void drawRectangle(Graphics2D g) {
		g.setColor(PColor.WHITE);
		g.drawRect((int)(x + xmap - cwidth / 2), (int)(y + ymap - cheight / 2), cwidth, cheight);
	}
	public boolean isRight() {
		return facingRight;
	}
	
	public void calculateCorners(double x, double y) {
		
		int leftTile = (int)(x - cwidth / 2) / tileSize;
		int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
		int topTile = (int)(y - cheight / 2) / tileSize;
		int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;
		
		int tl = getTileMap().getType(topTile, leftTile);
		int tr = getTileMap().getType(topTile, rightTile);
		int bl = getTileMap().getType(bottomTile, leftTile);
		int br = getTileMap().getType(bottomTile, rightTile);
		
		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight = br == Tile.BLOCKED;
		
		int leftTile2 = (int)(x - cwidth / 2) / tileSize;
		int rightTile2 = (int)(x + cwidth / 2 - 1) / tileSize;
		int topTile2 = (int)(y - 120 / 2) / tileSize;
		int bottomTile2 = (int)(y + 120 / 2 - 1) / tileSize;
		
		int tl2 = getTileMap().getType(topTile2, leftTile2);
		int tr2 = getTileMap().getType(topTile2, rightTile2);
		int bl2 = getTileMap().getType(bottomTile2, leftTile2);
		int br2 = getTileMap().getType(bottomTile2, rightTile2);

		if(!topLeft) topLeft = tl2 == Tile.BLOCKED;
		if(!topRight) topRight = tr2 == Tile.BLOCKED;
		if(!bottomLeft) bottomLeft = bl2 == Tile.BLOCKED;
		if(!bottomRight) bottomRight = br2 == Tile.BLOCKED;
		
		
	}
	public void checkTileMapCollision() {
		currCol = (int)x / tileSize;
		currRow = (int)y / tileSize;
		
		xdest = x + dx;
		ydest = y + dy;
		
		xtemp = x;
		ytemp = y;
		
		if(!noclip) {
			calculateCorners(x, ydest);
		}
		if (dy < 0) {
			if(topLeft || topRight) {
				dy = 0;
				ytemp = currRow * tileSize + cheight / 2;
			}
			else {
				ytemp += dy;
			}
		}
		if (dy > 0) {
			if(bottomLeft || bottomRight) {
				dy = 0;
				falling = false;
				ytemp = (currRow + 1) * tileSize - cheight / 2;
			}
			else {
				ytemp += dy;
			}
		}
		if(!noclip) {
			calculateCorners(xdest, y);
		}
		if (dx < 0) {
			if(topLeft || bottomLeft) {
				dx = 0;
				xtemp = currCol * tileSize + cwidth /2 ;
			}
			else {
				xtemp += dx;
			}
		}
		if(dx > 0) {
			if(topRight || bottomRight) {
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2;
			}
			else {
				xtemp += dx;
			}
		}
		if(!falling) {
			calculateCorners(x, ydest + 1);
			if(!bottomLeft && !bottomRight) {
				falling = true;
			}
		}
	}
		
		public int getx() {return (int)x; }
		public int gety() {return (int)y; }
		
		public double getxRound() {return PInteger.round(x, 2); }
		public double getyRound() {return PInteger.round(y, 2); }
		
		public int getwidth() {return width; }
		public int getheight() {return height; }
		public int getcwidth() {return cwidth; }
		public int getcheight() {return cheight; }
		
		public void setPosition (double x, double y) {
			this.x = x;
			this.y = y;
		}

		public void setVector (double dx, double dy) {
			this.x = dx;
			this.y = dy;
		}
		
		public void setMapPosition() {
			xmap = getTileMap().getx();
			ymap = getTileMap().gety();
		}
		
		public void setLeft (boolean b) {left = b; }
		public void setRight (boolean b) {right = b; }
		public void setUp (boolean b) {up = b; }
		public void setDown (boolean b) {down = b; }
		public void setJumping (boolean b) {jumping = b; }
		
		public void setEssential(boolean b) { essential = b; }
		public void setInvincible(boolean b) { invincible = b; }
		public boolean isEssential() { return essential; }
		public boolean isInvincible() { return invincible; }

		public boolean notOnScreen() {
			return x + xmap + width < 0 ||
				x + xmap - width > GamePanel.WIDTH ||
				y + ymap + height < 0 ||
				y + ymap - height > GamePanel.HEIGHT;
		}
		public abstract void update();
		public void draw(java.awt.Graphics2D g) {
			
			if(facingRight) {
				g.drawImage(
					animation.getImage(),
					(int)(x + xmap - width / 2),
					(int)(y + ymap - height / 2),
					null
				);
			}
			else {
				g.drawImage(
					animation.getImage(),
					(int)(x + xmap - width / 2 + width),
					(int)(y + ymap - height / 2),
					-width,
					height,
					null
				);
			}
		}
		public TileMap getTileMap() {
			return tileMap;
		}

		public void setTileMap(TileMap tileMap) {
			this.tileMap = tileMap;
		}
		public void setInWater(boolean b) {
			inwater = b;
		}
		public boolean inWater() {
			return inwater;
		}
		public final static int DIRECTION_UP = 0;
		public final static int DIRECTION_DOWN = 1;
		public final static int DIRECTION_RIGHT = 2;
		public final static int DIRECTION_LEFT = 3;
		public void push(int dir, double pow) {
			if(dir == DIRECTION_UP) {
				dy = -pow;
				falling = true;
			}
			if(dir == DIRECTION_DOWN) {
				dy = pow;
				falling = true;
			}
			if(dir == DIRECTION_LEFT) {
				dx = pow;
			}
			if(dir == DIRECTION_RIGHT) {
				dx = -pow;
			}
		}
		
}
