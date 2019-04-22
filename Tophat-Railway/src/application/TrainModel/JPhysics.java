package application.TrainModel;

/**
 * This is a simple 1d point mass physics simulation
 * 
 * 
 * @author jar99
 * @version 1.0
 *
 */

public class JPhysics {

	protected boolean debug = false;
	
	private static final double EPSILON = 0.00001;

	public static final double GRAVITY = 9.8;
	
	
	
	private double TIMEINTERVAL = 1e9;
	
	protected double MINVELOCITY = Double.NEGATIVE_INFINITY;

	protected double MAXVELOCITY = Double.POSITIVE_INFINITY;
	

	protected double MAXACCELERATION = Double.POSITIVE_INFINITY;
	
	private boolean limit = false;
	
	protected double STDFRICTION = 0.002;
	
	double acceleration;
	double velocity;
	double position;
	double displacement;
	

	// Drag values
	private double CD = 0.47; // The caracteristic of objects air resistance
	private double p = 1.2; // Desiaty of air

	final double A = 0.002865; // m^2
//		final double m = 0.04593; // kg

	private double mass = 7.5;
	private double angle = 45.0; //Negative angles are going down
	
	private double eIn;
	private double eOut;

	public JPhysics(double STDFRICTION, double TIMEINTERVAL, double MINVELOCITY, double MAXVELOCITY, double MAXACCELERATION) {
		this.TIMEINTERVAL = TIMEINTERVAL;
		this.STDFRICTION = STDFRICTION;
		
		this.MINVELOCITY = MINVELOCITY;
		this.MAXVELOCITY = MAXVELOCITY;
		this.MAXACCELERATION = MAXACCELERATION;
		this.limit = true;
	}

	public JPhysics(double x) {
		this.position = x;
	}

	public JPhysics(double x, double v) {
		this(x);
		this.velocity = v;
	}
	
	public JPhysics(double x, double v, boolean limit) {
		this(x, v);
		this.limit = limit;
	}


	private double f;
	
	public void update(long deltaT) {
		double dt = deltaT / TIMEINTERVAL;
		double redian = Math.toRadians(angle);

		double ff = kineticF(redian) + eOutF();

		double fp = powerF() - gravity(redian);

		double f = fp;

		if (!equal(velocity, 0.0, EPSILON)) { //This is used to stop on small values
			if (directionOfTravel()) {
				f -= ff;
			} else {
				f += ff;
			}
		}else {
			velocity = 0;
		}
			
		if(debug) System.out.printf("angle= %f\tin= %f\tk loss= %f\n", angle, fp,
				directionOfTravel()? -ff:+ff);

		double an = 0;
		an = f / mass;
		if(limit) an = Math.min(MAXACCELERATION , an);

		double vn = laplace(dt, acceleration, an, velocity);

		if(limit) vn = Math.max(MINVELOCITY , Math.min(MAXVELOCITY, vn));
		

		double xn = laplace(dt, velocity, vn, position);

		if(debug) {
			System.out.printf("f= %f\ta= %f\tv= %f\tx= %f\n", this.f, acceleration, velocity, position);
			System.out.printf("f= %f\ta= %f\tv= %f\tx= %f\n", f, an, vn, xn);
		}
		
		displacement = xn - position;
		position = xn;
		velocity = vn;
		acceleration = an;

		this.f = f;
	}

	protected double kineticF(double angle) {
		return STDFRICTION * normal(angle);
	}

	protected double gravity(double angle) {
		return GRAVITY * Math.sin(angle) * mass;
	}

	protected double normal(double angle) {
		return GRAVITY * Math.cos(angle) * mass;
	}

	protected double drag() {
		return 0.5 * p * Math.pow(velocity, 2) * CD * A;
	}

	private double powerF() {
		if(velocity == 0.0) return eIn;
		return eIn/velocity;
	}
	
	private double eOutF() {
		return eOut;
	}
	
	protected double laplace(double deltaT, double a, double an, double b) {
		return b + ((deltaT) / 2) * (an + a);
	}

    /**
     * Returns boolean value to direction of travel
     * Returns true if it is traveling in the positive direction
     * returns false in the negative direction.
     * @return
     */
    private boolean directionOfTravel() {
    	return velocity > 0.0;
    
    }
    
	private boolean equal(double a, double b, double epsilon) {
		return (Math.abs(a - b) < epsilon);
	}

	public void print() {
		System.out.printf("F = %f\n" + "a = %f\n" + "v = %f\n" + "x = %f\n", f, acceleration, velocity, position);
	}

	public double getPosition() {
		return position;
	}
	
	protected void seteIn(double eIn) {
		this.eIn = eIn;
	}

	protected void seteOut(double eOut) {
		this.eOut = eOut;
	}

	public void setAngle(double angle) {
		this.angle = angle;
		
	}

	public void setMass(double mass) {
		this.mass = mass;		
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;	
	}
}
