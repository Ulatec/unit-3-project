package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import static org.junit.Assert.*;


public class UserTest {
    private User newUser;
    private User newUser2;
    private Board testBoard;
    private Question question;
    private Answer answer;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        /* Arrange */
        testBoard = new Board("Testing");
        newUser = testBoard.createUser("testUser");
        newUser2 = testBoard.createUser("testUser2");
        question = newUser.askQuestion("Is this a test?");
        answer = newUser2.answerQuestion(question, "Yes it is.");
    }

    @Test
    public void nonAuthorCannotAcceptAnswers() throws Exception{
        thrown.expect(AnswerAcceptanceException.class);
        thrown.expectMessage("Only testUser can accept this answer as it is their question");
        /* Act */
        newUser2.acceptAnswer(answer);
        /* Assert */
    }
    public void authorCanAcceptAnswer() throws Exception{
        /* Act */
        newUser.acceptAnswer(answer);
        /* Assert */
        assertEquals(true, answer.isAccepted());
    }

    @Test
    public void authorIsNotAbleToDownVoteOwnPosts() throws Exception{

        int reputation = newUser.getReputation();
        /* Act */
        newUser.downVote(answer);
        /* Assert */
        assertEquals(reputation, newUser.getReputation());
    }

    @Test
    public void authorIsNotAbleToUpVoteOwnPosts() throws Exception{
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");
        /* Act */
        newUser2.upVote(answer);
        /* Assert */
    }
    @Test
    public void downVoteReducesReputationByOne() throws Exception{
        /* Act */
        newUser.downVote(answer);
        /* Assert */
        assertEquals(-1, newUser2.getReputation());
    }



    @Test
    public void answererReputationRaisesOnAnswerAccepted() throws Exception{
        /* Act */
        newUser.acceptAnswer(answer);
        /* Assert */
        assertEquals("Incorrect amount of reputation received", 15, newUser2.getReputation());
    }
    @Test
    public void answererReputationRaisesOnUpVote() throws Exception{
        /* Act */
       newUser.upVote(answer);
        /* Assert*/
       assertEquals("Incorrect amount of reputation received", 10, newUser2.getReputation());

    }

    @Test
    public void questionerReputationRaisesOnUpVote() throws Exception{
        /* Act */
        newUser2.upVote(question);
        /* Assert */
        assertEquals("Incorrect amount of reputation received",5, newUser.getReputation());

    }
}