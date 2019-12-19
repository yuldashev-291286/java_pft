package ru.stqa.pft.rest.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestAssuredTests extends TestBase {

  @Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = getIssues();
    System.out.println(oldIssues);
    Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test issue");
    int issueId = createIssue(newIssue);
    oldIssues.add(newIssue.withId(issueId));
    Set<Issue> newIssues = getIssues();
    assertEquals(newIssues, oldIssues);
  }

  @Test
  public void testCheckDefectResolved() throws IOException {
    skipIfNotFixed(2145);
    System.out.println("Corrected defect has the status: " + getStateNameIssue(2145));
    assertEquals(getStateNameIssue(2145), "Resolved");
  }

}
