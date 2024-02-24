import java.lang.Math;
import java.util.Random;

public class Main {
    public static double gravity(double m1, double m2, double r) {
        double G = 6.6743 * Math.pow(10, -11);

        return (G * m1 * m2)/Math.pow(r, 2);
    }

    public static Vec gravity_acc_vec(Vec from_vec, Vec to_vec, double from_mass, double to_mass) {
        double gravity = gravity(from_mass, to_mass, Vec.distance(from_vec, to_vec));
        double acc = gravity/from_mass;

        Vec norm_vec = (to_vec.sub(from_vec)).norm_vec();

        return norm_vec.mul(acc);
    }

    public static Vec[][] tick(Vec[] positions, Vec[] vels, double[] masses, double tick_time) {
        Vec[] new_positions = new Vec[positions.length];
        Vec[] new_vels = new Vec[vels.length];
        
        for (int i = 0; i < positions.length; i++) {
            Vec net_acc = new Vec(0, 0);

            for (int j = 0; j < positions.length; j++) {
                if (i != j) {
                    // Add the acceleration between each of the objects to the net acceleration
                    Vec acc_i_j = gravity_acc_vec(positions[i], positions[j], masses[i], masses[j]);
                    net_acc = net_acc.add(acc_i_j);
                }
            }

            Vec initial_pos = positions[i];
            Vec initial_vel = vels[i];

            // Use net_acc on object to get new velocity
            Vec new_vel = initial_vel.add(net_acc.mul(tick_time));

            // Trapezoidal Sum of Velocity to approximate new position
            double new_pos_x = initial_pos.x + tick_time * (0.5 * (initial_vel.x + new_vel.x));
            double new_pos_y = initial_pos.y + tick_time * (0.5 * (initial_vel.y + new_vel.y));
            Vec new_pos = new Vec(new_pos_x, new_pos_y);

            new_positions[i] = new_pos;
            new_vels[i] = new_vel;
        }

        Vec[][] pos_vel = {new_positions, new_vels};
        
        return pos_vel;
    }
    
    public static Vec[][] sim(Vec[] positions, Vec[] vels, double[] masses, double secs, double tick_time) {
        Vec[] current_positions = positions;
        Vec[] current_vels = vels;

        int num_ticks = (int)((1/tick_time) * secs);

        for (int t = 0; t < num_ticks; t++) {
            Vec[][] poses_and_vels = tick(current_positions, current_vels, masses, tick_time);

            current_positions = poses_and_vels[0];
            current_vels = poses_and_vels[1];
        }

        return new Vec[][] {current_positions, current_vels};
    }

    public static void main(String[] args) {
        // Creating random positions, 0 velocities, and all masses with 1
        Vec[] test_poses = {new Vec(0, 0), new Vec(148.9 * Math.pow(10, 9), 0)};
        Vec[] test_vels = {new Vec(0, 0), new Vec(0, 29624.77)};
        double[] masses = {1.99 * Math.pow(10, 30), 5.97 * Math.pow(10, 24)};

        // Running the simulation
        Vec[][] a = sim(test_poses, test_vels, masses, 1000 * 365 * 24 * 3600, 1.0/16);

        Vec[] final_poses = a[0];
        Vec[] final_vels = a[1];

        for (Vec position: final_poses) {
            position.print();
        }

    }
}
