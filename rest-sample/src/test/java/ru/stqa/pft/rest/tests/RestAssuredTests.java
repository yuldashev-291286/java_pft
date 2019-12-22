package ru.stqa.pft.rest.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RestAssuredTests extends TestBase {

  @Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = getIssues();
    Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test issue");
    String issueId = createIssue(newIssue);
    oldIssues.add(newIssue.withId(issueId));
    Set<Issue> newIssues = getIssues();

    assertEquals(newIssues, oldIssues);
  }

  @Test
  public void testCheckDefectStatus() throws IOException {
    Issue issue = getIssue(2303).iterator().next();
    skipIfNotFixed(issue);
    System.out.println("Defect has status: " + issue.getNameState());

    assertTrue(issue.getNameState().equals("Resolved"));
  }

}
