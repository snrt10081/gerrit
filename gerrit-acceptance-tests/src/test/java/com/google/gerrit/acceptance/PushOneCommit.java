import static com.google.gerrit.acceptance.GitUtil.add;
import static com.google.gerrit.acceptance.GitUtil.amendCommit;
import static com.google.gerrit.acceptance.GitUtil.createCommit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.gerrit.acceptance.GitUtil.Commit;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidTagNameException;
import org.eclipse.jgit.api.errors.NoHeadException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

  private static final String FILE_CONTENT = "some content";
        PersonIdent i);
  private final PersonIdent i;
  private String tagName;
      @Assisted PersonIdent i) {
    this(notesFactory, approvalsUtil, db, i, SUBJECT, FILE_NAME, FILE_CONTENT);
      @Assisted("content") String content) {
    this(notesFactory, approvalsUtil, db, i, subject, fileName, content, null);
      @Nullable @Assisted("changeId") String changeId) {
    this.i = i;
  public Result to(Git git, String ref)
      throws GitAPIException, IOException {
    add(git, fileName, content);
    return execute(git, ref);
  public Result rm(Git git, String ref)
      throws GitAPIException, IOException {
    GitUtil.rm(git, fileName);
    return execute(git, ref);
  private Result execute(Git git, String ref) throws GitAPIException,
      IOException, ConcurrentRefUpdateException, InvalidTagNameException,
      NoHeadException {
    Commit c;
    if (changeId != null) {
      c = amendCommit(git, i, subject, changeId);
    } else {
      c = createCommit(git, i, subject);
      changeId = c.getChangeId();
    if (tagName != null) {
      git.tag().setName(tagName).setAnnotated(false).call();
    return new Result(ref, pushHead(git, ref, tagName != null), c, subject);
  public void setTag(final String tagName) {
    this.tagName = tagName;
    private final Commit commit;
    private final String subject;
    private Result(String ref, PushResult result, Commit commit,
      this.result = result;
      this.subject = subject;
      return Iterables.getOnlyElement(
          db.changes().byKey(new Change.Key(commit.getChangeId()))).currentPatchSetId();
      return commit.getChangeId();
      return commit.getCommit().getId();
      return commit.getCommit();
      Change c =
          Iterables.getOnlyElement(db.changes().byKey(new Change.Key(commit.getChangeId())).toList());
      assertEquals(subject, c.getSubject());
      assertEquals(expectedStatus, c.getStatus());
      assertEquals(expectedTopic, Strings.emptyToNull(c.getTopic()));
      Set<Account.Id> expectedReviewerIds =
          Sets.newHashSet(Lists.transform(Arrays.asList(expectedReviewers),
              new Function<TestAccount, Account.Id>() {
                @Override
                public Account.Id apply(TestAccount a) {
                  return a.id;
                }
              }));

      for (Account.Id accountId
          : approvalsUtil.getReviewers(db, notesFactory.create(c)).values()) {
        assertTrue("unexpected reviewer " + accountId,
            expectedReviewerIds.remove(accountId));
      }
      assertTrue("missing reviewers: " + expectedReviewerIds,
          expectedReviewerIds.isEmpty());
      assertEquals(message(refUpdate),
          expectedStatus, refUpdate.getStatus());
      assertEquals(expectedMessage, refUpdate.getMessage());
      assertTrue(message(refUpdate), message(refUpdate).toLowerCase().contains(
          expectedMessage.toLowerCase()));