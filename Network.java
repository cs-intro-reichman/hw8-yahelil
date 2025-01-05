/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }
    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        for (int i = 0; i < userCount; i++){
            if (users[i].getName().equalsIgnoreCase(name)){
                return users[i];
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        if (userCount == this.users.length){
            return false;
        }
        for (int i = 0; i < this.users.length; i++){
            if (users[i] == null){
                users[i] = new User(name);
                userCount++;
                return true;
            }
            if (users[i].getName().equalsIgnoreCase(name)){
                return false;
            }
        }
        return false;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  oor if the "follows" addition failed fr some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        User user1 = getUser(name1);
        User user2 = getUser(name2);

        if (user1 == null || user2 == null || name1.equalsIgnoreCase(name2)){
            return false;
        }

        String[] user_followee = user1.getfFollows();
        for (int i = 0; i < user1.getfCount(); i++){
            if (user_followee[i].equalsIgnoreCase(name2)){
                return false;
            }
        }
        
        user1.addFollowee(name2);
        user2.addFollowee(name1);
        return true;
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        User mostRecommendedUserToFollow = null;
        int max_mutuals = 0;
        for (int i = 0; i < userCount; i++){
            if (users[i].getName().equalsIgnoreCase(name)){
                continue;
            }
            int mutuals = users[i].countMutual(getUser(name));
            if(mutuals > max_mutuals){
                max_mutuals = mutuals;
                mostRecommendedUserToFollow = users[i];
            }
        }

        return mostRecommendedUserToFollow.getName();
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        if (userCount == 0){
            return null;
        }
        User most_popular = users[0];
        int max_follow = followeeCount(users[0].getName()); //The number of followers the current most popular user has
        for (int i = 1; i < userCount; i++){
            int curr_user_count = followeeCount(users[i].getName());
            if (curr_user_count > max_follow){
                most_popular = users[i];
                max_follow = curr_user_count;
            }
        }
        return most_popular.getName();
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int follow_count = 0;
        for (int i = 0; i < userCount; i++){
            if (!users[i].getName().equalsIgnoreCase(name) && users[i].follows(name)){
                follow_count++;
            }
        }
        return follow_count;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
        String ans = "Network:";
        for (int i = 0; i < userCount; i++){
            ans += "\n" + users[i].toString();
        }
       return ans;
    }
}
