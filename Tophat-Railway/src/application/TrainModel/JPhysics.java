package application.TrainModel;

/**
 * <h1>JPhysics Engine</h1> This is a simple 1d point mass physics simulation
 * This system works with non consent acceleration
 * 
 * To run the program the update function needs to be called with the deltaTime.
 * 
 * @author jar99
 * @version 1.0
 * @since 2019-04-18
 *
 */
public class JPhysics {

	protected boolean debug = false; // Set to true to output the physics statements

	private static final double EPSILON = 0.00001; // This is the close enough value this needs to be as small as
													// posible

	public static final double GRAVITY = 9.8;

	private double TIMEINTERVAL = 1e9; // Second to Nanosecond

	// Default bound settings
	protected double MINVELOCITY = Double.NEGATIVE_INFINITY;

	protected double MAXVELOCITY = Double.POSITIVE_INFINITY;

	protected double MAXACCELERATION = Double.POSITIVE_INFINITY;

	private boolean limit = false; // If true the bound values will be used.

	protected double STDFRICTION = 0.0; // This is the standard friction this is zero if there is no friction

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
	private double angle = 45.0; // Negative angles are going down

	private double eIn; // Add energy into the system
	private double eOut; // Remove energy from the system

	/**
	 * Call this method to set all of the parameters This runs the physics in limit
	 * mode
	 * 
	 * @param STDFRICTION     sets the friction value
	 * @param TIMEINTERVAL    the convention to seconds
	 * @param MINVELOCITY     in m/s
	 * @param MAXVELOCITY     in m/s
	 * @param MAXACCELERATION in m/s^2
	 */
	public JPhysics(double STDFRICTION, double TIMEINTERVAL, double MINVELOCITY, double MAXVELOCITY,
			double MAXACCELERATION) {
		this.TIMEINTERVAL = TIMEINTERVAL;
		this.STDFRICTION = STDFRICTION;

		this.MINVELOCITY = MINVELOCITY;
		this.MAXVELOCITY = MAXVELOCITY;
		this.MAXACCELERATION = MAXACCELERATION;
		this.limit = true;
	}

	/**
	 * Creates an default object at location x
	 * 
	 * @param x location in meters
	 */
	public JPhysics(double x) {
		this.position = x;
	}

	/**
	 * Creates an object at location x with an initial velocity
	 * 
	 * @param x location in meters
	 * @param v velocity in m/s
	 */
	public JPhysics(double x, double v) {
		this(x);
		this.velocity = v;
	}

	/**
	 * Creates an object at location x with an initial velocity and run the program
	 * in limit mode
	 * 
	 * @param x
	 * @param v     velocity in m/s
	 * @param limit true if limits should be used else not
	 */
	public JPhysics(double x, double v, boolean limit) {
		this(x, v);
		this.limit = limit;
	}

	private double f; // The last force

	/**
	 * Call this function to update the physics of the object
	 * 
	 * @param deltaT the time that has elapsed since the program was run in seconds
	 */
	public void update(long deltaT) {
		double dt = deltaT / TIMEINTERVAL;
		double redian = Math.toRadians(angle);

		double ff = kineticF(redian) + eOutF();

		double fp = powerF() - gravity(redian);

		double f = fp;

		if (!equal(velocity, 0.0, EPSILON)) { // This is used to stop on small values
			if (directionOfTravel()) {
				f -= ff;
			} else {
				f += ff;
			}
		} else {
			velocity = 0;
		}

		if (debug)
			System.out.printf("angle= %f\tin= %f\tk loss= %f\n", angle, fp, directionOfTravel() ? -ff : +ff);

		double an = 0;
		an = f / mass;
		if (limit)
			an = Math.min(MAXACCELERATION, an);

		double vn = laplace(dt, acceleration, an, velocity);

		if (limit)
			vn = Math.max(MINVELOCITY, Math.min(MAXVELOCITY, vn));

		double xn = laplace(dt, velocity, vn, position);

		if (debug) {
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
		if (velocity == 0.0)
			return eIn;
		return eIn / velocity;
	}

	private double eOutF() {
		return eOut;
	}

	protected double laplace(double deltaT, double a, double an, double b) {
		return b + ((deltaT) / 2) * (an + a);
	}

	/**
	 * Returns boolean value to direction of travel Returns true if it is traveling
	 * in the positive direction returns false in the negative direction.
	 * 
	 * @return true if we are moving in the positive direction
	 */
	private boolean directionOfTravel() {
		return velocity > 0.0;

	}

	/**
	 * Equal close enough of a and b by a margin epsilon
	 * 
	 * @param a       value one
	 * @param b       value two
	 * @param epsilon the close enough approximation value
	 * @return true of a == b
	 */
	private boolean equal(double a, double b, double epsilon) {
		return (Math.abs(a - b) < epsilon);
	}

	/**
	 * Prints the current physics value of the object
	 */
	public void print() {
		System.out.printf("F = %f\n" + "a = %f\n" + "v = %f\n" + "x = %f\n", f, acceleration, velocity, position);
	}

	/**
	 * Gets the objects position of the object in meters
	 * 
	 * @return the position of object in meters
	 */
	public double getPosition() {
		return position;
	}

	/**
	 * Sets the force to add from the object
	 * 
	 * @param eIn the force in newtons
	 */
	protected void seteIn(double eIn) {
		this.eIn = eIn;
	}

	/**
	 * Sets the force to removed from the object
	 * 
	 * @param eIn the force in newtons
	 */
	protected void seteOut(double eOut) {
		this.eOut = eOut;
	}

	/**
	 * Sets the angle of the environment
	 * 
	 * @param angle in radians
	 */
	public void setAngle(double angle) {
		this.angle = angle;

	}

	/**
	 * Sets the mass of the object
	 * 
	 * @param mass in kilograms
	 */
	public void setMass(double mass) {
		this.mass = mass;
	}

	/**
	 * Sets the velocity of the object
	 * 
	 * @param velocity in m/s
	 */
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
}
