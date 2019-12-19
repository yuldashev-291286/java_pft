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
    Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test issue");
    String issueId = createIssue(newIssue);
    oldIssues.add(newIssue.withId(issueId));
    Set<Issue> newIssues = getIssues();

    assertEquals(newIssues, oldIssues);
  }

  @Test
  public void testCheckDefectResolved() throws IOException {
    skipIfNotFixed("2145");
    System.out.println("Open defect has status: " + getStateNameIssue("2145"));
    assertEquals(getStateNameIssue("2145"), "Open");
  }

}
