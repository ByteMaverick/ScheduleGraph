import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {

    @Test
    public void BaseTest() {
        Schedule schedule = new Schedule();
        schedule.insert(15); //adds job 0 with time 8
        Schedule.Job j1 = schedule.insert(3); //adds job 1 with time 3
        schedule.insert(5); //adds job 2 with time 5
        schedule.insert(61);
        schedule.finish(); //should return 8, since job 0 takes time 8 to complete.

        assertEquals(61, schedule.finish());
    }

    @Test
    public void FinishTest() {
        Schedule schedule = new Schedule();
        schedule.insert(8); //adds job 0 with time 8
        Schedule.Job j1 = schedule.insert(3); //adds job 1 with time 3
        schedule.insert(5); //adds job 2 with time 5

        schedule.finish();

        assertEquals(0, j1.start());

        schedule.get(0).requires(schedule.get(2));

        assertEquals(13, schedule.finish());
    }



    @Test
    public void StartTest() {
        Schedule schedule = new Schedule();
        Schedule.Job j1 = schedule.insert(8);
        Schedule.Job J2 = schedule.insert(1);
        Schedule.Job J3 = schedule.insert(2);
        Schedule.Job J4 = schedule.insert(15);

        assertEquals(15, schedule.finish());

        assertEquals(0, schedule.get(0).start());
        assertEquals(0, schedule.get(1).start());
        assertEquals(0, schedule.get(2).start());
        assertEquals(0, schedule.get(3).start());

        j1.requires(J2);
        j1.requires(J3);
        j1.requires(J4);

        assertEquals(15, schedule.get(0).start());
        assertEquals(0, schedule.get(1).start());
        assertEquals(0, schedule.get(2).start());
        assertEquals(0, schedule.get(3).start());


    }

    @Test
    public void ExampleTest() {
        Schedule schedule = new Schedule();
        schedule.insert(8); //adds job 0 with time 8
        Schedule.Job j1 = schedule.insert(3); //adds job 1 with time 3
        schedule.insert(5); //adds job 2 with time 5

        assertEquals(8, schedule.finish());

        schedule.get(0).requires(schedule.get(2));

        assertEquals(13, schedule.finish());
        schedule.get(0).requires(j1); //job 1 must precede job 0
        schedule.finish(); //should return 13

        assertEquals(13, schedule.finish());

        schedule.get(0).start(); //should return 5

        assertEquals(5, schedule.get(0).start());

        assertEquals(0, j1.start());
        assertEquals(0, schedule.get(2).start());

        j1.requires(schedule.get(2)); //job 2 must precede job 1

        schedule.finish(); //should return 16

        assertEquals(16, schedule.finish());

        schedule.get(0).start(); //should return 8
        schedule.get(1).start(); //should return 5
        schedule.get(2).start(); //should return 0

        assertEquals(8, schedule.get(0).start());
        assertEquals(5, schedule.get(1).start());
        assertEquals(0, schedule.get(2).start());

        schedule.get(1).requires(schedule.get(0)); //job 0 must precede job 1 (creates loop)
        assertEquals(-1, schedule.finish());

        assertEquals(-1, schedule.get(0).start());
        assertEquals(-1, schedule.get(1).start());
        assertEquals(0, schedule.get(2).start());
    }



    @Test

    public void FinishTest0() {

        Schedule schedule = new Schedule();

        Schedule.Job j1 = schedule.insert(8);
        Schedule.Job J2 = schedule.insert(1);
        Schedule.Job J3 = schedule.insert(2);
        Schedule.Job J4 = schedule.insert(15);


        // no relation between nodes

        assertEquals(15,schedule.finish());

        // with one connection between 0 and 1

        j1.requires(J2);

        assertEquals(15,schedule.finish());

        // with two connections to zero

        j1.requires(J3);

        assertEquals(15,schedule.finish());

        // with three connections

        j1.requires(J4);

        assertEquals(23,schedule.finish());




    }


    @Test

    public void topoOrderFinish() {

        Schedule schedule = new Schedule();

        Schedule.Job j1 = schedule.insert(8);
        Schedule.Job J2 = schedule.insert(1);
        Schedule.Job J3 = schedule.insert(2);
        Schedule.Job J4 = schedule.insert(15);


        J2.requires(J4);
        J4.requires(J3);
        J3.requires(j1);


        assertEquals(26,schedule.finish());



    }


    @Test

    public void twoIndependentRelations() {

        Schedule schedule = new Schedule();

        Schedule.Job j1 = schedule.insert(8);
        Schedule.Job J2 = schedule.insert(1);
        Schedule.Job J3 = schedule.insert(2);
        Schedule.Job J4 = schedule.insert(15);


        J2.requires(j1);
        J4.requires(J3);


        assertEquals(17,schedule.finish());



    }

    @Test

    public void bigStartTest() {
        Schedule schedule = new Schedule();

        Schedule.Job j1 = schedule.insert(8);
        Schedule.Job J2 = schedule.insert(1);
        Schedule.Job J3 = schedule.insert(2);
        Schedule.Job J4 = schedule.insert(15);


        // no relation between nodes

        assertEquals(15,schedule.finish());

        // with one connection between 0 and 1

        assertEquals(0,j1.start());
        assertEquals(0,J2.start());
        assertEquals(0,J2.start());
        assertEquals(0,J2.start());

        j1.requires(J2);

        assertEquals(15,schedule.finish());

        assertEquals(1,j1.start());
        assertEquals(0,J2.start());
        assertEquals(0,J2.start());
        assertEquals(0,J2.start());
        // with two connections to zero

        j1.requires(J3);


        assertEquals(15,schedule.finish());

        assertEquals(2,j1.start());
        assertEquals(0,J2.start());
        assertEquals(0,J2.start());
        assertEquals(0,J2.start());

        // with three connections

        j1.requires(J4);

        assertEquals(15,j1.start());
        assertEquals(0,J2.start());
        assertEquals(0,J2.start());
        assertEquals(0,J2.start());



        assertEquals(23,schedule.finish());


    }





    @Test
    public void sparseGraph() {
        Schedule schedule = new Schedule();

        Schedule.Job j1 = schedule.insert(7);
        Schedule.Job j2 = schedule.insert(6);
        Schedule.Job j3 = schedule.insert(2);
        Schedule.Job j4 = schedule.insert(15);
        Schedule.Job j5 = schedule.insert(8);
        Schedule.Job j6 = schedule.insert(10);
        Schedule.Job j7 = schedule.insert(3);
        Schedule.Job j8 = schedule.insert(12);
        Schedule.Job j9 = schedule.insert(5);
        Schedule.Job j10 = schedule.insert(9);
        Schedule.Job j11 = schedule.insert(4);
        Schedule.Job j12 = schedule.insert(11);
        Schedule.Job j13 = schedule.insert(13);
        Schedule.Job j14 = schedule.insert(1);
        Schedule.Job j15 = schedule.insert(14);
        Schedule.Job j16 = schedule.insert(16);
        Schedule.Job j17 = schedule.insert(18);
        Schedule.Job j18 = schedule.insert(20);
        Schedule.Job j19 = schedule.insert(17);
        Schedule.Job j20 = schedule.insert(19);


        assertEquals(20,schedule.finish());

        j20.requires(j18);
        j18.requires(j14);


        assertEquals(40,schedule.finish());


        j1.requires(j5);
        j18.requires(j14);


        assertEquals(40,schedule.finish());

        assertEquals(0, schedule.get(11).start());
        assertEquals(0, schedule.get(12).start());
        assertEquals(0, schedule.get(2).start());
        assertEquals(0, schedule.get(4).start());
        assertEquals(0, schedule.get(15).start());








    }
    @Test
    public void StartTestBeforeCallingFinish() {
        Schedule schedule = new Schedule();
        Schedule.Job j1 = schedule.insert(8);
        Schedule.Job J2 = schedule.insert(1);
        Schedule.Job J3 = schedule.insert(2);
        Schedule.Job J4 = schedule.insert(15);


        assertEquals(0, schedule.get(0).start());
        assertEquals(0, schedule.get(1).start());
        assertEquals(0, schedule.get(2).start());
        assertEquals(0, schedule.get(3).start());

        j1.requires(J2);
        j1.requires(J3);
        j1.requires(J4);

        assertEquals(15, schedule.get(0).start());
        assertEquals(0, schedule.get(1).start());
        assertEquals(0, schedule.get(2).start());
        assertEquals(0, schedule.get(3).start());

        // adding a relation
    }



    @Test

    public void emptyGraph() {
        Schedule schedule = new Schedule();

        assertEquals(0, schedule.finish());

    }


    @Test

    public void singleNode() {
        Schedule schedule = new Schedule();

        Schedule.Job j1 = schedule.insert(8);
        assertEquals(8, schedule.finish());

    }



    @Test
    public void basicCycle() {
        Schedule schedule = new Schedule();

        Schedule.Job j1 = schedule.insert(8);
        Schedule.Job J2 = schedule.insert(1);

        j1.requires(J2);
        J2.requires(j1);

        assertEquals(-1, schedule.finish());

    }


    @Test
    public void basicCycle2() {
        Schedule schedule = new Schedule();

        Schedule.Job j1 = schedule.insert(8);
        Schedule.Job J2 = schedule.insert(1);

        j1.requires(J2);
        J2.requires(j1);

        assertEquals(-1, schedule.finish());
        assertEquals(-1, J2.start());
        assertEquals(-1, j1.start());

    }


    @Test
    public void CycleDetection() {
        Schedule schedule = new Schedule();

        Schedule.Job j1 = schedule.insert(8);
        Schedule.Job J2 = schedule.insert(1);
        Schedule.Job J3 = schedule.insert(2);

        j1.requires(J2);
        J2.requires(j1);
        J2.requires(J3);


        assertEquals(-1, schedule.finish());
        assertEquals(0, J3.start());
        assertEquals(-1, J2.start());



    }



    @Test
    public void testComplexDependencies() {
        Schedule schedule = new Schedule();
        Schedule.Job j1 = schedule.insert(5);
        Schedule.Job j2 = schedule.insert(10);
        Schedule.Job j3 = schedule.insert(3);
        Schedule.Job j4 = schedule.insert(7);

        j2.requires(j1);
        j3.requires(j1);
        j4.requires(j2);

        assertEquals(22, schedule.finish());
        assertEquals(0, j1.start());
        assertEquals(5, j2.start());
        assertEquals(5, j3.start());
        assertEquals(15, j4.start());
    }

    // this is pro causing timeout
    @Test

    public void AllNodeAreInACycle() {


        Schedule schedule = new Schedule();


        Schedule.Job job0 = schedule.insert(8);
        Schedule.Job job1 = schedule.insert(3);
        Schedule.Job job2 = schedule.insert(5);
        Schedule.Job job3 = schedule.insert(1);
        Schedule.Job job4 = schedule.insert(2);
        Schedule.Job job5 = schedule.insert(3);

        job0.requires(job1);
        job1.requires(job2);
        job2.requires(job3);
        job3.requires(job4);
        job4.requires(job5);




        assertEquals(22,schedule.finish());


        job2.requires(job0);

        assertEquals(-1,schedule.finish());

        assertEquals(0,job5.start());

        assertEquals(3,job4.start());
        assertEquals(5,job3.start());
        assertEquals(-1,job2.start());
        assertEquals(-1,job1.start());





    }

}