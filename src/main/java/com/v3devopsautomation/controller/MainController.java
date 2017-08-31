package com.v3devopsautomation.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.core.singletons.BuildStatus;
import com.core.singletons.DisplayRepository;
import com.core.threads.BuildThread;
import com.v3devopsautomation.DAO.GroupDAO;
import com.v3devopsautomation.DAO.HostNameDAO;
import com.v3devopsautomation.DAO.RegisterDAO;
import com.v3devopsautomation.model.GroupNames;
import com.v3devopsautomation.model.HostNames;
import com.v3devopsautomation.model.Login;
import com.v3devopsautomation.model.Register;
import com.v3devopsautomation.model.SingleServer;

@Controller
// @PropertySource("classpath:databases.properties")
public class MainController {
	@Value("${svnurl}")
	String svnurl;
	@Value("${svnUserName}")
	String svnUserName;
	@Value("${svnPassword}")
	String svnPassword;
	private BuildThread build;
	private boolean isFirstReq = false;
	private ThreadGroup buildGroup = new ThreadGroup("VitehcBuild");
	private final Logger logger = LoggerFactory.getLogger(MainController.class);

	private int buildCount = 0;
	// @Autowired
	DisplayRepository dr;
	@Autowired
	RegisterDAO redao;
	@Autowired
	GroupDAO groupdao;
	@Autowired
	HostNameDAO hostdao;

	String purpose;
	Thread t;
	String res;

	@Autowired
	@Qualifier("RegisterValidator")
	private Validator registervalidator;

	@Autowired
	@Qualifier("LoginValidator")
	private Validator loginvalidator;
	@Autowired
	@Qualifier("GroupNameValidator")
	private Validator groupnamevalidator;
	@Autowired
	@Qualifier("HostNameValidator")
	private Validator hostnamevalidator;

	@InitBinder("userForm")
	private void initBinderregister(WebDataBinder binder) {
		binder.setValidator(registervalidator);
	}

	@Autowired
	@Qualifier("SingleServerValidator")
	private Validator singleServerValidator;

	@InitBinder("singleServerForm")
	private void initBindersingleserver(WebDataBinder binder) {
		binder.setValidator(singleServerValidator);
	}

	@InitBinder("loginForm")
	private void initBinderlogin(WebDataBinder binder) {
		binder.setValidator(loginvalidator);
	}

	@InitBinder("groupForm")
	private void initBinderGroup(WebDataBinder binder) {
		binder.setValidator(groupnamevalidator);
	}

	@InitBinder("hostForm")
	private void initBinderhost(WebDataBinder binder) {
		binder.setValidator(hostnamevalidator);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(ModelMap model) {
		return "index";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String Logout(ModelMap model, HttpSession session) {

		session.setAttribute("username", "");

		return "index";
	}

	@RequestMapping(value = "/Dashboard", method = RequestMethod.GET)
	public String Dashboard(ModelMap model, HttpSession session) {

		session.setAttribute("username", session.getAttribute("username"));
		return "DashBoard";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String signin(ModelMap model) {
		model.addAttribute("loginForm", new Login());
		return "sign-in";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String signup(ModelMap model) {
		model.addAttribute("userForm", new Register());
		return "Sign-up";
	}

	@RequestMapping(value = "/addgroup", method = RequestMethod.GET)
	public String addgroup(ModelMap model, HttpSession session) {

		model.addAttribute("groupForm", new GroupNames());
		session.setAttribute("username", session.getAttribute("username"));
		return "AddGroup";
	}

	@RequestMapping(value = "/addhost", method = RequestMethod.GET)
	public String addhost(ModelMap model, HttpSession session) {
		List<GroupNames> check = groupdao.allGroup();

		Map<String, String> grouplist = new HashMap<String, String>();
		for (GroupNames entity : check) {
			grouplist.put(entity.getGroupname(), entity.getGroupname());
		}
		model.addAttribute("grouplist", grouplist);
		model.addAttribute("hostForm", new HostNames());
		session.setAttribute("username", session.getAttribute("username"));
		return "AddIPS";
	}

	@RequestMapping(value = "/singleserver", method = RequestMethod.GET)
	public String singleserver(ModelMap model, HttpSession session) {
		List<GroupNames> check = groupdao.allGroup();

		Map<String, String> grouplist = new HashMap<String, String>();
		for (GroupNames entity : check) {
			grouplist.put(entity.getGroupname(), entity.getGroupname());
		}
		dr = new DisplayRepository();
		dr.setupLibrary();
		List<String> result = dr.svrepository(svnurl, svnUserName, svnPassword);
		Map<String, String> repolists = new HashMap<String, String>();
		for (String re : result) {
			System.out.println(re);
			repolists.put(re, re);
		}
		// setupLibrary();
		model.addAttribute("grouplist", grouplist);
		model.addAttribute("singleServerForm", new SingleServer());
		model.addAttribute("roleslist", repolists);
		session.setAttribute("username", session.getAttribute("username"));
		return "SelectGroupIP";
	}

	@RequestMapping(value = "/rungroup", method = RequestMethod.GET)
	public String Groupserver(ModelMap model, HttpSession session) {
		List<GroupNames> check = groupdao.allGroup();

		Map<String, String> grouplist = new HashMap<String, String>();
		for (GroupNames entity : check) {
			grouplist.put(entity.getGroupname(), entity.getGroupname());
		}
		dr = new DisplayRepository();
		dr.setupLibrary();
		List<String> result = dr.svrepository(svnurl, svnUserName, svnPassword);
		Map<String, String> repolists = new HashMap<String, String>();
		for (String re : result) {
			System.out.println(re);
			repolists.put(re, re);
		}
		// setupLibrary();
		model.addAttribute("grouplist", grouplist);
		model.addAttribute("singleServerForm", new SingleServer());
		model.addAttribute("roleslist", repolists);
		session.setAttribute("username", session.getAttribute("username"));
		return "GroupServer";
	}

	@RequestMapping(value = "/gethostlist", method = RequestMethod.POST)
	public @ResponseBody List<HostNames> getSubcatList(@RequestParam("groupname") String groupname) {
		List<HostNames> check = hostdao.getIPList(groupname);
		return check;
	}

	@ModelAttribute("userForm")
	public Register createStudentModel() {
		return new Register();
	}

	@ModelAttribute("loginForm")
	public Login loginModel() {
		return new Login();
	}

	@RequestMapping(value = "/startBuild", method = RequestMethod.GET)
	public @ResponseBody String startBuild(ModelMap model, HttpSession session)
			throws IOException, InterruptedException {

		String buildName = "VBuild" + buildCount;
		// isFirstReq = true;
		int active = Thread.activeCount();
		System.out.println("currently active threads: " + active);
		// Thread.sleep(2000l);
		build = new BuildThread(buildName);
		Thread innThread = new Thread(buildGroup, build, buildName);
		buildCount++;
		innThread.start();
		System.out.println("Main thread: " + innThread.getName() + "(" + innThread.getId() + ")");
		System.out.println(build.buildId());
		BuildStatus buildStatus = BuildStatus.getBuildStatuIns();
		Map<String, StringBuffer> mapBuildStatus = buildStatus.getMapBuildStatus();
		for (String key : mapBuildStatus.keySet()) {
			System.out.println("key : " + key);

		}
		return "Build Started with name : " + buildName;
	}

	@RequestMapping(value = "/getBuildStatus/{buildName}", method = RequestMethod.GET)
	public @ResponseBody String getBuildStatus(@PathVariable String buildName, HttpSession session)
			throws IOException, InterruptedException {

		BuildStatus buildStatus = BuildStatus.getBuildStatuIns();
		Map<String, StringBuffer> mapBuildStatus = buildStatus.getMapBuildStatus();
		for (String key : mapBuildStatus.keySet()) {
			System.out.println("key : " + key);

		}
		if (mapBuildStatus.containsKey(buildName)) {

			return mapBuildStatus.get(buildName).toString();
		} else {
			return "Invalid Build Name";
		}
	}

	@RequestMapping(value = "/addStudent", method = RequestMethod.POST)
	public String User(ModelMap model, @ModelAttribute("userForm") @Validated Register re,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "Sign-up";
		}
		String result = redao.registerUser(re);
		model.addAttribute("message", result);

		return "index";

	}

	@RequestMapping(value = "/group", method = RequestMethod.POST)
	public String Group(ModelMap model, @ModelAttribute("groupForm") @Validated GroupNames groupname,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "AddGroup";
		}
		String result = groupdao.insertGroup(groupname);
		model.addAttribute("message", result);

		return "AddGroup";

	}

	@RequestMapping(value = "/host", method = RequestMethod.POST)
	public String host(ModelMap model, @ModelAttribute("hostForm") @Validated HostNames hostname,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "AddIPS";
		}
		System.out.println("Group Names");
		System.out.println(hostname.getGroupname());
		String result = hostdao.insertGroup(hostname);
		model.addAttribute("message", result);

		return "AddIPS";

	}

	@RequestMapping(value = "/request", method = RequestMethod.POST)
	public String login(ModelMap model, @ModelAttribute("loginForm") @Validated Login login,
			BindingResult bindingResult, HttpSession session) {

		if (bindingResult.hasErrors()) {
			return "sign-in";
		}
		List<Register> li2 = redao.validateLogin(login.getUsername(), login.getPassword());
		int itemCount = li2.size();

		if (itemCount == 1) {
			session.setAttribute("username", login.getUsername());
			return "DashBoard";

		} else {

			model.addAttribute("message", "Invalid Username and password");
			return "sign-in";
		}
	}

}