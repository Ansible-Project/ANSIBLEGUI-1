package com.core.singletons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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

public class DisplayRepository {
	public  void setupLibrary() {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
    }
	public  List<String> listEntries(SVNRepository repository, String path)
            throws SVNException {
        Collection entries = repository.getDir(path, -1, null,
                (Collection) null);
        System.out.println(path);
        Iterator iterator = entries.iterator();
        List<String> rolenames=new ArrayList();
        while (iterator.hasNext()) {
            SVNDirEntry entry = (SVNDirEntry) iterator.next();
            System.out.println(entry.getName());
            rolenames.add(entry.getName());
        }
        return rolenames;
    }
	public  List<String> svrepository(String svnurl, String svnUserName, String svnPassword)
	{
		 SVNRepository repository = null;
		 List<String> rolenames = null;
	        try {
	            /*
	             * Creates an instance of SVNRepository to work with the repository.
	             * All user's requests to the repository are relative to the
	             * repository location used to create this SVNRepository.
	             * SVNURL is a wrapper for URL strings that refer to repository locations.
	             */
	            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnurl));
	        } catch (SVNException svne) {
	            /*
	             * Perhaps a malformed URL is the cause of this exception
	             */
	            System.err
	                    .println("error while creating an SVNRepository for location '"
	                            + svnurl + "': " + svne.getMessage());
	            System.exit(1);
	        }

	        /*
	         * User's authentication information (name/password) is provided via  an 
	         * ISVNAuthenticationManager  instance.  SVNWCUtil  creates  a   default 
	         * authentication manager given user's name and password.
	         * 
	         * Default authentication manager first attempts to use provided user name 
	         * and password and then falls back to the credentials stored in the 
	         * default Subversion credentials storage that is located in Subversion 
	         * configuration area. If you'd like to use provided user name and password 
	         * only you may use BasicAuthenticationManager class instead of default 
	         * authentication manager:
	         * 
	         *  authManager = new BasicAuthenticationsManager(userName, userPassword);
	         *  
	         * You may also skip this point - anonymous access will be used. 
	         */
	        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(svnUserName, svnPassword);
	        repository.setAuthenticationManager(authManager);

	        try {
	            /*
	             * Checks up if the specified path/to/repository part of the URL
	             * really corresponds to a directory. If doesn't the program exits.
	             * SVNNodeKind is that one who says what is located at a path in a
	             * revision. -1 means the latest revision.
	             */
	            SVNNodeKind nodeKind = repository.checkPath("", -1);
	            if (nodeKind == SVNNodeKind.NONE) {
	                System.err.println("There is no entry at '" + svnurl + "'.");
	                System.exit(1);
	            } else if (nodeKind == SVNNodeKind.FILE) {
	                System.err.println("The entry at '" + svnurl + "' is a file while a directory was expected.");
	                System.exit(1);
	            }
	            /*
	             * getRepositoryRoot() returns the actual root directory where the
	             * repository was created. 'true' forces to connect to the repository 
	             * if the root url is not cached yet. 
	             */
	            System.out.println("Repository Root: " + repository.getRepositoryRoot(true));
	            /*
	             * getRepositoryUUID() returns Universal Unique IDentifier (UUID) of the 
	             * repository. 'true' forces to connect to the repository 
	             * if the UUID is not cached yet.
	             */
	            System.out.println("Repository UUID: " + repository.getRepositoryUUID(true));
	            System.out.println("");

	            /*
	             * Displays the repository tree at the current path - "" (what means
	             * the path/to/repository directory)
	             */
	          rolenames=listEntries(repository, "");
	        } catch (SVNException svne) {
	            System.err.println("error while listing entries: "
	                    + svne.getMessage());
	            System.exit(1);
	        }
	      
		return rolenames;
	        
	}

}
