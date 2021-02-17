import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public final class CsvWriter {
    public static void writeToCsv(String fileName, List<RequestTracker> requests) throws FileNotFoundException {
        var csv = new File(fileName);
        var writer = new PrintWriter(csv);
        requests.stream().map(CsvWriter::convertToCsv).forEach(writer::println);
    }

    private static String convertToCsv(RequestTracker request) {
        return request.getStartTime() + "," + request.getEndTime() + "," + request.getLatency() + "," + request.getRequestType().name() + "," + (request.getResponseCode() == null ? "API_EXCEPTION" : request.getResponseCode());
    }
}
