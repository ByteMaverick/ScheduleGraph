
import java.util.*;


public class Schedule {


    private ArrayList <Job> jobs = new ArrayList<>();;
    private Map<Job, List<Job>> adjList = new HashMap<>();
    private Map<Job, Integer> startTime = new HashMap<>();
    private boolean cycleDetected = false;

    public Schedule() {

    }
    // Done, should work.
    public Job insert(int time) {

        if(time>=0) {
            Job job = new Job(time);
            jobs.add(job);
            adjList.put(job, new ArrayList<>());
            return job;
        }
        return null;


    }
    // Done, should work.
    public Job get(int index) {
        return jobs.get(index);
    }


    public int finish() {


        List<Job> sortedList = TopOrder(); // This should work.


        // This is the logic he mentioned in the video,it should work.
        // Cycle Detection, returns -1 if detected.
        if(sortedList.size() != adjList.size()) {
            for (Job job : jobs) {
                job.setStartTime(-1);
            }
            return -1;
        }

        for (Job job : jobs) {
            startTime.put(job, 0);
        }

        // relaxation shit.
        //outer loop should work.

        for (Job job : sortedList) {
            for (Job dep: adjList.get(job)) {
                int newStartTime = startTime.get(job) + job.time;
                if (newStartTime > startTime.get(dep)) {


                    startTime.put(dep, newStartTime);

                }
            }
        }

        int maxFinishTime = 0;

        for (Job job : jobs) {

            int finishTime = startTime.get(job) + job.time;
            job.setStartTime(startTime.get(job));

            if (finishTime > maxFinishTime)
            {
                maxFinishTime = finishTime;

            }
        }

        return maxFinishTime;




    }

    // Done, should work flawlessly.
    private List<Job> TopOrder() {

        Map<Job, Integer> inDegree = new HashMap<>();
        List<Job> topologicalSortedJobs = new ArrayList<>();



        for(Job job: jobs) {
            inDegree.put(job,0);
        }
        // Done, works
        // get the in degree
        for(Job job: jobs) {
            for(Job i: adjList.get(job)) {
                inDegree.put(i, inDegree.get(i) + 1);
            }
        }

        Queue<Job> queue = new LinkedList<>();
        for(Job job: jobs) {
            if(inDegree.get(job) == 0) {

                queue.add(job);
            }
        }
        while(!queue.isEmpty()) {

            Job current = queue.remove();
            topologicalSortedJobs.add(current);

            for(Job dep: adjList.get(current)) {

                inDegree.put(dep, inDegree.get(dep)-1);
                if(inDegree.get(dep) == 0) {

                    queue.add(dep);
                }
            }
        }
        return topologicalSortedJobs;


    }



    public class Job{

        public int time;
        public List<Job> preReq = new ArrayList<>();;
        public int startTime = 0;




        public void setStartTime(int s) {
            this.startTime = s;
        }

        public Job(int time) {
            this.time = time;

        }

        // Done, should work.
        public void requires(Job j) {
            preReq.add(j);
            adjList.get(j).add(this);
        }

        public int start() {


            return startTime;
        }

    }
    public static void main(String[] args) {
        // Create a new Schedule instance
        Schedule schedule = new Schedule();

        // Insert jobs with their respective completion times
        Schedule.Job job0 = schedule.insert(8); // Job 0 takes 8 time units
        Schedule.Job job1 = schedule.insert(3); // Job 1 takes 3 time units
        Schedule.Job job2 = schedule.insert(5); // Job 2 takes 5 time units

        // Set up job dependencies
        job0.requires(job2); // Job 2 must finish before Job 0 can start
        job0.requires(job1); // Job 1 must finish before Job 0 can start

        // Display the initial finish time of the schedule
        System.out.println("Initial finish time: " + schedule.finish()); // Should return 13

        // Modify dependencies to introduce new conditions
        job1.requires(job2); // Job 2 must finish before Job 1 can start
        System.out.println("Updated finish time after adding new dependencies: " + schedule.finish()); // Should return 16

        // Display the start times for each job
        System.out.println("Start time for Job 0: " + job0.start()); // Should return 8
        System.out.println("Start time for Job 1: " + job1.start()); // Should return 5
        System.out.println("Start time for Job 2: " + job2.start()); // Should return 0

        // Create a cycle for testing
        job1.requires(job0); // This introduces a cycle
        System.out.println("Finish time after introducing cycle: " + schedule.finish()); // Should return -1 (cycle detected)
        System.out.println("Start time for Job 0 after introducing cycle: " + job0.start()); // Should return -1 (can't start due to cycle)
        System.out.println("Start time for Job 1 after introducing cycle: " + job1.start()); // Should return -1 (can't start due to cycle)
        System.out.println("Start time for Job 2 after introducing cycle: " + job2.start()); // Should return 0 (no cycle, can start)

        // Additional test cases can be added here to further validate your implementation
    }






}