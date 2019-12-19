package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

public class GithubTests {

  @Test
  public void testCommits() throws IOException {
    Github github = new RtGithub("4a5835e5f2cf2c6740f0e3aa7f4629b224e3b298");
    RepoCommits commits = github.repos()
            .get(new Coordinates.Simple("yuldashev-291286", "java_pft")).commits();
    for (RepoCommit commit : commits.iterate((Map<String, String>) new ImmutableMap.Builder<String, String>().build())) {
      System.out.println(new RepoCommit.Smart(commit).message());
    }
  }

}
