package linkedincoursera.services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import linkedincoursera.model.careerbuilder.JobSearchResult;
import linkedincoursera.model.careerbuilder.ResponseJobSearch;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by melissa on 5/11/15.
 */
@Component
public class CareerBuilderService {
    private static final String DEVELOPER_KEY = "WD8G6WQ6KL1RX5Q7V0PY";
    private static final String HOSTSITE = "US";
    private static final String CAREER_BUILDER_JOB_SEARCH_URL =
            "http://api.careerbuilder.com/v1/jobsearch?DeveloperKey={0}&HostSite={1}&Keywords={2}";

    public List<JobSearchResult> fetchJobs(String skill) throws Exception {
        String url = MessageFormat.format(CAREER_BUILDER_JOB_SEARCH_URL, DEVELOPER_KEY, HOSTSITE, skill);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        String resultStr = result.toString().substring(result.toString().indexOf("<ResponseJobSearch>"));
        XmlMapper xmlmapper = new XmlMapper();
        ResponseJobSearch responseJobSearch = xmlmapper.readValue(resultStr, ResponseJobSearch.class);
        return responseJobSearch.getResults();
    }
}