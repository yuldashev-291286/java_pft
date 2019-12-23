package ru.stqa.pft.rest.tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertTrue;

public class TestBase {

  @BeforeClass
  public void init() {
    RestAssured.authentication = RestAssured.basic("288f44776e7bec4bf44fdfeb1e646490", "");
  }

  public Set<Issue> getIssues() throws IOException {
    String json = RestAssured.get("https://bugify.stqa.ru/api/issues.json?limit=300").asString();
    JsonElement parsed = JsonParser.parseString(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {}.getType());
  }

  public Set<Issue> getIssue(int idIssue) throws IOException {
    String json = RestAssured.get("https://bugify.stqa.ru/api/issues/" + idIssue + ".json").asString();
    JsonElement parsed = JsonParser.parseString(json);
    JsonElement issue = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issue, new TypeToken<Set<Issue>>() {}.getType());
  }

  public String createIssue(Issue newIssue) throws IOException {
    String json = RestAssured.given()
            .parameter("subject", newIssue.getSubject())
            .parameter("description", newIssue.getDescription())
            .parameter("state_name", newIssue.getNameState())
            .parameter("state", newIssue.getState())
            .post("https://bugify.stqa.ru/api/issues.json").asString();
    JsonElement parsed = JsonParser.parseString(json);
    return parsed.getAsJsonObject().get("issue_id").getAsString();
  }

  public boolean isIssueOpen(Issue issue) throws IOException {
    if (issue.getNameState().equals("Open") == true) {
      return true;
    } else {
      return false;
    }
  }

  public void skipIfNotFixed(Issue issue) throws IOException {
    if (isIssueOpen(issue)) {
      throw new SkipException("Ignored because of issue " + issue.getId());
    }
  }

}
