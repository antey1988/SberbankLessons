package refactoring2;


import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SalaryHtmlReportNotifier {

    private final Connection connection;

    public SalaryHtmlReportNotifier(Connection databaseConnection) {
        this.connection = databaseConnection;
    }

    public void generateAndSendHtmlSalaryReport(String departmentId, LocalDate dateFrom, LocalDate dateTo, String recipients) {
        try {
            // execute query and get the results
            ResultSet results = getSQLResult(departmentId, dateFrom, dateTo);
            // create a StringBuilder holding a resulting html
            StringBuilder resultingHtml = getHTMLTextFromSQL(results);
            // send report to the recipients list
            sendMessage(recipients, resultingHtml.toString());
        } catch (SQLException | MessagingException e) {
            e.printStackTrace();
        }
    }

    private ResultSet getSQLResult(String departmentId, LocalDate dateFrom, LocalDate dateTo) throws SQLException {
        // prepare statement with sql
        PreparedStatement ps = connection.prepareStatement("select emp.id as emp_id, emp.name as amp_name, sum(salary) as salary from employee emp left join" +
                "salary_payments sp on emp.id = sp.employee_id where emp.department_id = ? and" +
                " sp.date >= ? and sp.date <= ? group by emp.id, emp.name");
        // inject parameters to sql
        ps.setString(0, departmentId);
        ps.setDate(1, new java.sql.Date(dateFrom.toEpochDay()));
        ps.setDate(2, new java.sql.Date(dateTo.toEpochDay()));
        return ps.executeQuery();
    }

    private StringBuilder getHTMLTextFromSQL(ResultSet resultSet) throws SQLException {
        // create a StringBuilder holding a resulting html
        StringBuilder resultingHtml = new StringBuilder();
        resultingHtml.append("<html><body><table><tr><td>Employee</td><td>Salary</td></tr>");
        double totals = 0;
        while (resultSet.next()) {
            // process each row of query results
            resultingHtml.append("<tr>"); // add row start tag
            resultingHtml.append("<td>").append(resultSet.getString("emp_name")).append("</td>"); // appending employee name
            resultingHtml.append("<td>").append(resultSet.getDouble("salary")).append("</td>"); // appending employee salary for period
            resultingHtml.append("</tr>"); // add row end tag
            totals += resultSet.getDouble("salary"); // add salary to totals
        }
        resultingHtml.append("<tr><td>Total</td><td>").append(totals).append("</td></tr>");
        resultingHtml.append("</table></body></html>");
        return resultingHtml;
    }

    private void sendMessage(String recipients, String htmlText) throws MessagingException {
        // now when the report is built we need to send it to the recipients list
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // we're going to use google mail to send this message
        mailSender.setHost("mail.google.com");
        // construct the message
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(recipients);
        // setting message text, last parameter 'true' says that it is HTML format
        helper.setText(htmlText, true);
        helper.setSubject("Monthly department salary report");
        // send the message
        mailSender.send(message);
    }
}
