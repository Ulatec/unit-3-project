package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mark on 3/23/2017.
 */
public class UserTest {
    private User newUser;
    private User newUser2;
    private Board testBoard;
    private Question question;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        testBoard = new Board("Testing");
        newUser = testBoard.createUser("testUser");
        newUser2 = testBoard.createUser("testUser2");
        question = newUser.askQuestion("Is this a test?");
    }

    @Test
    public void onlyAuthorCanAcceptAnswers() throws Exception{
        thrown.expect(AnswerAcceptanceException.class);
        thrown.expectMessage("Only testUser can accept this answer as it is their question");

        Answer answer = newUser2.answerQuestion(question, "Yes it is.");

        newUser2.acceptAnswer(answer);
    }
    @Test
    public void authorIsNotAbleToVoteOnOwnPosts() throws Exception{
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");

        Answer answer = newUser.answerQuestion(question, "Yes it is.");

        newUser.upVote(answer);

    }
    @Test
    public void downVoteReducesReputationByOne() throws Exception{
        Answer answer = newUser2.answerQuestion(question, "Yes it is.");

        newUser.downVote(answer);
        assertEquals(-1, newUser2.getReputation());
    }

    @Test
    public void authorIsNotAbleToDownVoteOwnPosts() throws Exception{
        //thrown.expect(VotingException.class);
        //thrown.expectMessage("You cannot vote for yourself!");

        Answer answer = newUser.answerQuestion(question, "Yes it is.");
        int reputation = newUser.getReputation();
        newUser.downVote(answer);
        assertEquals(reputation, newUser.getReputation());
    }

    @Test
    public void answererReputationRaisesOnAnswerAccepted() throws Exception{
        Answer answer = newUser2.answerQuestion(question, "Yes it is.");

        newUser.acceptAnswer(answer);

        assertEquals("Incorrect amount of reputation received", 15, newUser2.getReputation());

    }

    @Test
    public void answererReputationRaisesOnUpVote() throws Exception{
       Answer answer = newUser2.answerQuestion(question, "Yes it is.");

       newUser.upVote(answer);

       assertEquals("Incorrect amount of reputation received", 10, newUser2.getReputation());

    }

    @Test
    public void questionerReputationRaisesOnUpVote() throws Exception{

        newUser2.upVote(question);

        assertEquals("Incorrect amount of reputation received",5, newUser.getReputation());

    }
    @Test
    public void getName() throws Exception {

        User user = testBoard.getUsers().get(0);
        //assertNull(newUser);
        assertEquals("Incorrect User Name", "testUser", user.getName());

    }

}