package linkedincoursera.controller;


import linkedincoursera.model.careerbuilder.JobSearchResult;
import linkedincoursera.model.coursera.Categories;
import linkedincoursera.model.coursera.Course;
import linkedincoursera.model.linkedin.Educations;
import linkedincoursera.model.linkedin.LinkedinUser;
import linkedincoursera.model.stackoverflow.QuestionCountSOF;
import linkedincoursera.model.udacity.UdacityCourse;
import linkedincoursera.repository.CourseraRepo;
import linkedincoursera.repository.UdacityRepo;
import linkedincoursera.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Controller
@PropertySource(value = {"classpath:/properties/application.properties"},ignoreResourceNotFound = false)
public class MainController {
    static boolean toBeInserted= true;
    @Value("${api.key}")
    private String apikey;
    @Value("${api.secret}")
    private String apisecret;
    private String redirect_uri = "http%3A%2F%2Flocalhost%3A8080%2Fauth%2Flinkedin";
    @Autowired
    AuthorizationService authService;
    @Autowired
    LinkedinService linkedinService;
    @Autowired
    CourseraService courseraService;
    @Autowired
    UdacityService udacityService;
    @Autowired
    public StackoverflowService stackoverflowService;
    @Autowired
    public CareerBuilderService careerBuilderService;
    @Autowired
    public CourseraRepo courseraRepo;
    @Autowired
    public UdacityRepo udacityRepo;

    private String access_token="";

    @RequestMapping("/")
    public String index() {
//        String url = "https://www.linkedin.com/uas/oauth2/authorization?response_type=code&client_id="+apikey+"&redirect_uri="+redirect_uri+"&state=987654321&scope=r_emailaddress";
//        return "redirect:"+url;
            return "login";
    }
    @RequestMapping("/signin")
    public String signin() {
        String url = "https://www.linkedin.com/uas/oauth2/authorization?response_type=code&client_id="+apikey+"&redirect_uri="+redirect_uri+"&state=987654321&scope=r_emailaddress";
        return "redirect:"+url;
    }
    @RequestMapping("/signout")
    public String signout() {
        return "login";
    }
    @RequestMapping("/login")
    public String login() {
        return "greeting";
    }
    @RequestMapping("/dashboard")
    public String homepage(Model model, HttpServletRequest request,HttpServletResponse response) {
        LinkedInProfile basicProf = linkedinService.getLinkedInProfile();
        LinkedinUser user = linkedinService.findUserByEmail(basicProf.getEmailAddress());
        getDetails(model, basicProf, user);
        return "dashboard";
    }
    @RequestMapping("/recommendation")
    public String recommendScreen(Model model) {
        LinkedInProfile basicProf = linkedinService.getLinkedInProfile();
        List<String> skillSet = linkedinService.getSkillSet();
        model.addAttribute("skills",skillSet);
        model.addAttribute("userName", basicProf.getFirstName() + " " + basicProf.getLastName());
        return "recommendations";
    }

    public List<Course> recommendCoursera(List<String> skillSet) {
        List<Course> courses = new ArrayList<Course>();

        try {
            if(skillSet.size() > 3) {
                skillSet = skillSet.subList(0, 3);
            }

            ArrayList<Course> initialCourses = new ArrayList<Course>();
            for(String skill : skillSet) {
                List<Course> courseraCourses = courseraService.fetchCourses(skill);
                initialCourses.addAll(courseraCourses);
            }

            for (Course course : initialCourses) {
                if(course.getLanguage().equals("en")) {
                    courses.add(course);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        if(courses.size() > 5) {
            courses = courses.subList(0, 5);
        }

        return courses;
    }

    public List<UdacityCourse> recommendUdacity(List<String> skillSet) {
        List<UdacityCourse> courses = new ArrayList<UdacityCourse>();

        try {
            if(skillSet.size() > 3) {
                skillSet = skillSet.subList(0, 3);
            }
            System.out.println(skillSet);
            for(String skill : skillSet) {
                List<UdacityCourse> udacityCourses = udacityService.fetchCourses();
                List<UdacityCourse> filteredUdacityCourses = UdacityService.searchCourses(udacityCourses, skill);
                courses.addAll(filteredUdacityCourses);
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        if(courses.size() > 5) {
            courses = courses.subList(0, 5);
        }

        return courses;
    }

    public List<JobSearchResult> recommendJobs(List<String> skillSet) {
        List<JobSearchResult> jobs = new ArrayList<JobSearchResult>();
        HashSet<JobSearchResult> recoJobs = new HashSet<JobSearchResult>();
        try {
            if(skillSet.size() > 3) {
                skillSet = skillSet.subList(0, 3);
            }

            for(String skill : skillSet) {
                List<JobSearchResult> jobSearchResults = careerBuilderService.fetchJobs(skill);
                for(JobSearchResult job:jobSearchResults) {
                    if(job.getCompany()!=null)
                        jobs.add(job);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        for(JobSearchResult job:jobs) {
            recoJobs.add(job);
        }
        List<JobSearchResult> resultJobs = new ArrayList<JobSearchResult>(recoJobs);
        if(resultJobs.size() > 10) {
            resultJobs = resultJobs.subList(0, 20);
        }
        return resultJobs;
    }

    public ArrayList<String> listSkillsByPopularity(List<String> skillSet) {
        ArrayList<String> orderedSkillSet = new ArrayList<String>();

        ArrayList<String> skills = new ArrayList<String>();
        for(String skill : skillSet) {
            skills.add(skill.toLowerCase());
        }

        try {
            List<QuestionCountSOF> tags = stackoverflowService.fetchMostAskedQuestionsStackoverflow();
            for(QuestionCountSOF tag : tags) {
                if(skills.contains(tag.getName().toLowerCase())) {
                    orderedSkillSet.add(tag.getName());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return orderedSkillSet;
    }
    @RequestMapping("/auth/linkedin")
    public String authenticate(Model model, @RequestParam String code, @RequestParam String state, HttpServletResponse response) {
        access_token = authService.authorizeLinkedinByPost(code, redirect_uri, apikey, apisecret);
        linkedinService.setApi(access_token);
        LinkedInProfile basicProf = linkedinService.getLinkedInProfile();
        LinkedinUser user = linkedinService.findUserByEmail(basicProf.getEmailAddress());
        if(user!=null) {
            response.addCookie(new Cookie("userEmail",basicProf.getEmailAddress()));
            getDetails(model, basicProf, user);
        }
        return "dashboard";
    }

    public void getDetails(Model model, LinkedInProfile basicProf, LinkedinUser user) {
        try {
            String emailAdd = basicProf.getEmailAddress();
            String name = linkedinService.getLinkedInProfile().getFirstName()+' '+linkedinService.getLinkedInProfile().getLastName();
            String profilePhoto = linkedinService.getLinkedInProfile().getProfilePictureUrl();
            String headLine = linkedinService.getLinkedInProfile().getHeadline();
            String summary  = linkedinService.getLinkedInProfile().getSummary();
            // UTILITY TO INSERT USER
 //            linkedinService.insertUser(name, emailAdd, profilePhoto, headLine, summary);
                String profilePhotoUrl = user.getProfilePhotoUrl();
            System.out.println(user.getProfilePhotoUrl());
                List<String> skillSet = user.getSkillSet();
                List<Educations> educationsList = user.getEducation();
                List<Course> courses = courseraService.fetchCourses();
                List<Categories> categoryList = courseraService.getCategoriesList();

//            model.addAttribute("userName", basicProf.getFirstName() + " " + basicProf.getLastName());
                model.addAttribute("userName", user.getUserName());
                model.addAttribute("profilePhotoUrl", profilePhotoUrl);
                model.addAttribute("education", educationsList);
                model.addAttribute("headline", user.getHeadline());
                model.addAttribute("skills", skillSet);
                model.addAttribute("summary", user.getSummary());
                model.addAttribute("courses", courses);
                model.addAttribute("positions", user.getPositions());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value ="/courses/{userEmail}/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> saveMyCourse(@RequestBody Course course, @PathVariable String userEmail) {
        System.out.println(course.getName());
        try {
            courseraService.saveCourse(course, userEmail);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @RequestMapping(value ="/courses/{userEmail}/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deleteMyCourse(@PathVariable String userEmail, @PathVariable Integer id) {
        courseraService.deleteCourse(id, userEmail);
        return new ResponseEntity<String>(HttpStatus.OK);

    }

    @RequestMapping(value ="/recommendations/courses", method=RequestMethod.GET)
//    @ResponseBody
    public String recommendCourses(Model model) {
        try {
            LinkedInProfile basicProf = linkedinService.getLinkedInProfile();
            LinkedinUser user = linkedinService.findUserByEmail(basicProf.getEmailAddress());
            List<String> skillsByPopularity = listSkillsByPopularity(user.getSkillSet());
            HashMap<String, ArrayList<Integer>> userCourseMap = courseraService.fetchCoursesOfUser(user.getEmail());

            List<Course> recommendedCoursera = recommendCoursera(skillsByPopularity);
            List<UdacityCourse> recommendedUdacity = recommendUdacity(skillsByPopularity);
            System.out.println(recommendedUdacity);
//            ArrayList allCourses = new ArrayList();
//            allCourses.addAll(recommendedCoursera);
//            allCourses.addAll(recommendedUdacity);
//            System.out.println("COURSERA:");
            for (Course course : recommendedCoursera) {
                if(course.getStartDay() == 0 && course.getStartMonth()==0 && course.getStartYear()==0)
                    recommendedCoursera.remove(course);
                if(userCourseMap!=null && userCourseMap.get(user.getEmail())!=null && userCourseMap.get(user.getEmail()).contains(course.getId())) {
                    course.setSavedCourse("true");
                }
            }
//            System.out.println("UDACITY:");
//            for(UdacityCourse course : recommendedUdacity) {
//                System.out.println(course.getTitle());
//            }
            model.addAttribute("userEmail", user.getEmail());
            model.addAttribute("courseraCourses",recommendedCoursera);
            model.addAttribute("udacityCourses",recommendedUdacity);
//            model.addAttribute("courses", allCourses);
//            return allCourses;
        } catch (Exception e) {
            e.printStackTrace();
//            return new ArrayList();
        }
        return "courses";
    }

    @RequestMapping(value ="/recommendations/jobs", method=RequestMethod.GET)
//    @ResponseBody
    public String recommendJobs(Model model) {
        try {
//            List<String> skillsByPopularity = listSkillsByPopularity(linkedinService.getSkillSet());
            LinkedInProfile basicProf = linkedinService.getLinkedInProfile();
            LinkedinUser user = linkedinService.findUserByEmail(basicProf.getEmailAddress());
            List<String> skillsByPopularity = listSkillsByPopularity(user.getSkillSet());
            List<JobSearchResult> recommendedJobs = recommendJobs(skillsByPopularity);

            model.addAttribute("jobs", recommendedJobs);
//            return recommendedJobs;
        } catch (Exception e) {
            e.printStackTrace();
//            return new ArrayList<JobSearchResult>();
        }
        return "job";
    }
}