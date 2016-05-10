package org.wintermartens.kartgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
/**
 *
 * Created by Cedric Martens on 2016-05-09.
 */
public class Kart
{
	private KartGame kartgame;

	private Texture texture;

	private Vector2 position;
	private float yaw; //in radians (direction)

	private float width, height;

	private Vector2 movement;
	private float topSpeed;

	private float acceleration;
	private float deceleration;

	private float turning;
	private float brakeSpeed;

	private float reverseAcceleration;
	private float reverseTopSpeed;

	private int currentLineId;
	private int currentLap;

	private Vector2 baseLoc;


	public Kart(KartGame kartgame, Vector2 position, float yaw, float topSpeed, float acceleration, float deceleration, float turning, float brakeSpeed, int currentLineId, int currentLap)
	{
		this.kartgame = kartgame;

		this.texture = this.kartgame.getTexture("kart");
		this.position = position;
		this.yaw = yaw;
		this.movement = new Vector2();
		this.topSpeed = topSpeed;
		this.acceleration = acceleration;
		this.deceleration = deceleration;
		this.turning = turning;
		this.brakeSpeed = brakeSpeed;
		this.reverseAcceleration = acceleration / 2;
		this.reverseTopSpeed = topSpeed / 2;
		this.currentLineId = currentLineId;
		this.currentLap = currentLap;
		this.baseLoc = new Vector2(position);
	}

	public void update(float deltaTime)
	{
		move(deltaTime);
	}

	public void turn(float delta, boolean clockwise)
	{
		int dir = 1;

		if(clockwise)
			dir = -1;

		yaw += dir * turning * delta * movement.len();
	}

	public void move(float deltaTime)
	{
		//Brake has priority over acceleration button
		//Would be nice to be able to configure keys in menu
		if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)
		|| Gdx.input.isKeyPressed(Input.Keys.DOWN))
		{
			//if speed is NOT very close to 0
			//decelerate
			//else speed == 0
			//then if the key is still pressed after 0.x seconds
			//Start being in reverse mode

		}
		else if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.UP))
		{
			accelerate(deltaTime);
		}
		else
		{
			if(!movement.isZero())
				decelerate(deltaTime, false);
		}


		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
			yaw += 0.1;

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			yaw -= 0.1;

		position.add(movement.x * deltaTime, movement.y * deltaTime);
	}

	public void replace()
	{

	}

	/**
	 * Accelerates the kart up to topSpeed, is also used to decelerate while moving back
	 */
	private void accelerate(float deltaTime)
	{
		if(movement.isZero())
		{
			movement.set(acceleration, 0);
			movement.setAngleRad(yaw);
		}

		if(movement.len() < topSpeed)
			movement.setLength(movement.len() + acceleration);
	}

	private void decelerate(float deltaTime, boolean isBraking)
	{
		if(movement.len() < 2)
			movement = new Vector2(0,0);

		if(isBraking)
		{
			movement.setLength(movement.len() - deceleration * brakeSpeed);

		}else{
			movement.setLength(movement.len() - deceleration);
		}
	}

	public void draw(SpriteBatch batch)
	{
		batch.draw(texture, position.x - 30, position.y - 20, 60, 40);
	}

	private Vector2 getDirection(float direction)
	{
		return new Vector2((position.x + MathUtils.cos(direction)) * 3f, (position.y + MathUtils.sin(direction)) * 3f);
	}

	public KartGame getKartgame()
	{
		return kartgame;
	}

	public void setKartgame(KartGame kartgame)
	{
		this.kartgame = kartgame;
	}

	public Vector2 getPosition()
	{
		return position;
	}

	public void setPosition(Vector2 position)
	{
		this.position = position;
	}

	public float getYaw()
	{
		return yaw;
	}

	public void setYaw(float yaw)
	{
		this.yaw = yaw;
	}

	public Vector2 getMovement()
	{
		return movement;
	}

	public void setMovement(Vector2 movement)
	{
		this.movement = movement;
	}

	public float getTopSpeed()
	{
		return topSpeed;
	}

	public void setTopSpeed(float topSpeed)
	{
		this.topSpeed = topSpeed;
	}

	public float getAcceleration()
	{
		return acceleration;
	}

	public void setAcceleration(float acceleration)
	{
		this.acceleration = acceleration;
	}

	public float getDeceleration()
	{
		return deceleration;
	}

	public void setDeceleration(float deceleration)
	{
		this.deceleration = deceleration;
	}

	public float getTurning()
	{
		return turning;
	}

	public void setTurning(float turning)
	{
		this.turning = turning;
	}

	public float getBrakeSpeed()
	{
		return brakeSpeed;
	}

	public void setBrakeSpeed(float brakeSpeed)
	{
		this.brakeSpeed = brakeSpeed;
	}

	public float getReverseAcceleration()
	{
		return reverseAcceleration;
	}

	public void setReverseAcceleration(float reverseAcceleration)
	{
		this.reverseAcceleration = reverseAcceleration;
	}

	public float getReverseTopSpeed()
	{
		return reverseTopSpeed;
	}

	public void setReverseTopSpeed(float reverseTopSpeed)
	{
		this.reverseTopSpeed = reverseTopSpeed;
	}

	public int getCurrentLineId()
	{
		return currentLineId;
	}

	public void setCurrentLineId(int currentLineId)
	{
		this.currentLineId = currentLineId;
	}

	public int getCurrentLap()
	{
		return currentLap;
	}

	public void setCurrentLap(int currentLap)
	{
		this.currentLap = currentLap;
	}

	public Vector2 getBaseLoc()
	{
		return baseLoc;
	}

	public void setBaseLoc(Vector2 baseLoc)
	{
		this.baseLoc = baseLoc;
	}
}
