import java.util.*;


public class Schedule {


    ArrayList <Job> jobs;

    public Schedule() {
        jobs = new ArrayList<>();
    }

    public Job insert(int time) {

        Job job = new Job(time);
        jobs.add(job);
        return job;
    }

    public Job get(int index) {
        return jobs.get(index);
    }


    public int finish() {


        boolean  hasCycle = CycleDetection();
        int earliestCompletionTime = 0;


        for (Job job : jobs) {
            if (hasCycle) {
                return -1;
            }
            earliestCompletionTime = Math.max(earliestCompletionTime, job.start() + job.time);
        }


        return earliestCompletionTime;

    }


    private boolean CycleDetection() {

        Map<Job, Integer> inDegree = new HashMap<>();


        boolean cycleDetected = false;


        for(Job job: jobs) {
            inDegree.put(job,0);
        }



        for(Job job: jobs) {
            for(Job dep: job.preReq) {

                inDegree.put(dep, inDegree.get(dep) + 1);
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




            for(Job dep: current.preReq) {

                inDegree.put(dep, inDegree.get(dep)-1);
                if(inDegree.get(dep) == 0) {

                    queue.add(dep);
                }
            }

        }

        for (Job job : jobs) {
            if (inDegree.get(job) > 0) {

                cycleDetected = true;

                break;

            }


        }

        return cycleDetected;


    }


    public class Job{

        public int time;
        public List<Job> preReq;
        private Map<Job, Integer> memo = new HashMap<>();

        public Job(int time) {
            this.time = time;
            preReq = new ArrayList<>();
        }

        public void requires(Job j) {
            preReq.add(j);
        }

        public int start() {


            for(Job job: jobs) {
                memo.put(job, 0);
            }
            if (this.preReq.size() == 0) {

                memo.put(this, 0);
                return 0;    }

            else if(!CycleDetection()) {

                if(memo.get(this) != 0) {
                    return memo.get(this);
                }

                int maxStartTime = 0;


                for (Job job : this.preReq) {

                    maxStartTime = Math.max(maxStartTime, job.start() + job.time);

                }

                memo.put(this, maxStartTime);


                return maxStartTime;


            }

            return -1;
        }
    }
}