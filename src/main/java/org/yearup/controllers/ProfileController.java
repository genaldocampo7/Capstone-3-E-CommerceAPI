package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("profile")
@CrossOrigin
public class ProfileController {
    private ProfileDao profileDao;
    private UserDao userDao;

    public ProfileController(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    @GetMapping()
    @PreAuthorize("permitAll()")
    public Profile getById(Principal principal) {
        User user = userDao.getByUserName(principal.getName());
        System.out.println(user);

        try {
            var profile = profileDao.getByUserId(user.getId());

            if(profile == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            return profile;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    public Profile addProfile(@RequestBody Profile profile)
    {
        try {
            return profileDao.create(profile);
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @PutMapping()
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @CrossOrigin
    public void updateProfile(Principal principal, @RequestBody Profile profile) {

        User user = userDao.getByUserName(principal.getName());

        try {
            var profile2 = profileDao.getByUserId(user.getId());

            if(profile2 == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            profileDao.update(profile2.getUserId(), profile);
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }

    }

    // added delete profile method as an optional feature

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteProfile(@PathVariable int id) {

        try {
            var profile = profileDao.getByUserId(id);

            if(profile == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            profileDao.delete(id);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }

    }
}
