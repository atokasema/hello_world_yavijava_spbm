package main.java.com.samples;

import java.net.URL;

import org.apache.log4j.Logger;

import com.vmware.spbm.PbmProfileResourceType;
import com.vmware.spbm.mo.PbmServiceInstance;
import com.vmware.vim25.mo.ServiceInstance;

public class Main {
	static Logger log = Logger.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		ServiceInstance si = null;
		try {
			if (args.length != 3) {
				System.out.println("Example: java -jar hello_world_yavijava_spbm.jar 102.126.234.43 administrator@vsphere.local password");
				System.exit(1);
			}

			 String vcenter = args[0];
			 String username = args[1];
			 String passwd = args[2];

			final URL url = new URL("https", vcenter, "/sdk");
			si = new ServiceInstance(url, username, passwd, true);
			System.out.println("Current vCenter Srever Time: " + si.currentTime());

			String cookieVal = si.getServerConnection().getSessionStr();
			/*
			String[] tokens = cookieVal.split(";");
			tokens = tokens[0].split("=");
			String extractedCookie = tokens[1];
			String cookieVal = "vcSessionCookie=" + extractedCookie;
			*/
			final URL pbmUrl = new URL("https", vcenter, "/pbm");
			PbmServiceInstance pbmSi = new PbmServiceInstance(pbmUrl, cookieVal, true);
			PbmProfileResourceType[] PbmProfileResourceTypes = pbmSi
					.getProfileManager().pbmFetchResourceType();
			System.out.println(PbmProfileResourceTypes.length);

		} finally {
			if (si != null) {
				si.getServerConnection().logout();
			}
		}
	}
}
