package com.revature.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.revature.models.Picture;

public class PictureService {

		static final String profileLoc = "pictures/profiles/profile";
		static final String receiptLoc = "pictures/receipts/receipt";
	
		public boolean createPicture(Picture pic, String loc) {
			String fileName = String.format("-%d.jpeg", pic.resourceId);

			try {
			    // convert stream to file
				Path path = Paths.get(loc + fileName);
				System.out.println(path);
			    Files.copy(pic.data.content(), Paths.get(loc + fileName));
			    return true;
			} catch (IOException ex) {
			    ex.printStackTrace();
			}
			
			
			return false;
		}
		
		public InputStream getPicture(int resourceId, String loc) {
			String fileName = String.format("-%d.jpeg", resourceId);
			Path path = Paths.get(loc + fileName);
			
			try {
			    // convert stream to file
				System.out.println(path);
				InputStream picture = Files.newInputStream(path);
			    //Files.copy(pic.data.content(), Paths.get(profileLocation + fileName));
			    return picture;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
			
			return null;
		}
}
