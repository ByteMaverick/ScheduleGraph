import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {

    @Test
    public void BasicTest() {
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

        schedule.finish(); //should return 8, since job 0 takes time 8 to complete.

        assertEquals(0, j1.start());

        schedule.get(0).requires(schedule.get(2));

        assertEquals(13, schedule.finish());
    }



    @Test
    public void Start() {
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

        // adding a relation
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

    public void BigFinishTest() {

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
}