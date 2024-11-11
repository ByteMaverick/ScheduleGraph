import java.util.*;


public class Schedule {


    private ArrayList <Job> jobs = new ArrayList<>();;
    private List<Job> cachedTopologicalOrder = null;


    public Schedule() {}

    public Job insert(int time) {

        Job job = new Job(time);
        jobs.add(job);
        return job;
    }

    public Job get(int index) {
        return jobs.get(index);
    }

    // First calls the TopOrder to get the topological order
    // Then run a modified version of the DAG SSSP algorithm,  instead of calculating the shortest path  it calculates the longest path.
    public int finish() {


        List<Job> sortedList = TopOrder();
        Map<Job, Integer> startTime = new HashMap<>();
        int maxFinishTime = 0;


        if(sortedList.size() != jobs.size()  ||  sortedList == null ) {	return -1;	}


        for (Job job : jobs) {	startTime.put(job, 0);	}



        for (Job job : sortedList) {

            for (Job dep: job.outgoing) {
                relax( startTime, job, dep);
            }
        }

        for (Job job : jobs) {

            int finishTime = startTime.get(job) + job.time;

            if (finishTime > maxFinishTime)
            {
                maxFinishTime = finishTime;

            }
        }

        return maxFinishTime;

    }

    // Relaxation done in a private method, since its used multiple times
    private void relax(Map<Job, Integer> startTime,Job job,Job dep) {
        int newStartTime = startTime.get(job) + job.time;
        if (newStartTime > startTime.get(dep)) {

            startTime.put(dep, newStartTime);

        }
    }
    // TopOrder() uses the Khan's Algorithm to calculate the topological order.
    public List<Job> TopOrder() {

        if (cachedTopologicalOrder != null) {
            return cachedTopologicalOrder;
        }

        Map<Job, Integer> inDegree = new HashMap<>();
        List<Job> topologicalSortedJobs = new ArrayList<>();



        for(Job job: jobs) {
            inDegree.put(job,0);
        }

        // Finding in-degree
        for(Job job: jobs) {
            for(Job i: job.outgoing) {
                inDegree.put(i, inDegree.get(i) + 1);
            }
        }
        // Storing jobs in inDegree zero in a queue.
        Queue<Job> queue = new LinkedList<>();
        for(Job job: jobs) {
            if(inDegree.get(job) == 0) {

                queue.add(job);
            }
        }
        while(!queue.isEmpty()) {

            Job current = queue.remove();
            topologicalSortedJobs.add(current);

            for(Job dep: current.outgoing) {

                inDegree.put(dep, inDegree.get(dep)-1);
                if(inDegree.get(dep) == 0) {

                    queue.add(dep);
                }
            }
        }


        cachedTopologicalOrder = topologicalSortedJobs;
        return cachedTopologicalOrder;


    }

    public class Job{

        public int time;
        private List<Job> outgoing = new ArrayList<>();

        public Job(int time) {
            this.time = time;
        }

        public void requires(Job j) {
            j.outgoing.add(this);
            cachedTopologicalOrder = null;

        }

        public int start() {


            List<Job> sortedList = TopOrder();
            if(!sortedList.contains(this)) {	return -1;	}


            return startHelper(this,sortedList);

        }


        public int startHelper(Job target, List<Job> sortedList) {

            Map<Job, Integer> startTime = new HashMap<>();

            for (Job job : jobs) {
                startTime.put(job, 0);
            }

            for (Job job : sortedList) {


                for (Job dependency: job.outgoing) {
                    relax(startTime, job, dependency);
                }
                if (job == target) {
                    return startTime.get(target);
                }

            }
            return startTime.get(target);
        }

    }
}