package thread;

import dao.SubmissionDAO;

public class SubmissionUploadThread extends Thread {

    private int assignmentId;
    private int studentId;
    private String filePath;

    public SubmissionUploadThread(int assignmentId, int studentId, String filePath) {
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        SubmissionDAO dao = new SubmissionDAO();
        dao.saveSubmission(assignmentId, studentId, filePath);
    }
}
