# CST338_Project2
eCommerce project using Android Studio

11/28 - First commit.  Created splash screen (SplashActivity) and login (MainActivity).  Need to work on login functionality.  Need to create/setup the database for Users. The background image I used in my application is from:  <a href="https://i.pinimg.com/originals/da/4e/01/da4e01b18b65ffb43d0a849b837e954b.jpg">FROM HERE</a>.<br><br>

11/29 - Added the Room stuff although it is not wired up yet to anything.  Created both login and register activities.  No functionality yet.<br><br>

11/30 - Didn't make as much progress as I would have liked.  I updated design in many activities and added some colors.  I would like to use Fragments, but I need to learn how.  I would like to connect the Users database to the login/register actions.  I would also like to figure out how to customize the top menu bar.<br><br>

12/1 - Login works from MainActivity.  Signup works from RegisterUser.  Logout and delete account works from ShopperHome.  Still want to refine some functionality.  TODO notes the future changes to implement.  Not sure preferences does anything or how it works.  Needs more research into it.  I also want to create custom dialog boxes.<br><br>

12/2 - Forgot what I did this day or pushed up.  I just know I did stuff.  How did I lose a day?<br><br>

12/3 - SharedPreferences now works.  Users can login to shopper and admin accounts, given they have the correct credentials.  Updated UI appeal.  Now that the basic activities are set, time to get real functionality from this program.  I need Admins to be able to manage the users.  Both admin and shopper need to interact with inventory and orders, so those database tables need to be set up.  Do I go with seperate activity per button click or learn how to set up fragments?<br><br>

12/4 - Admin user management side is almost done.  Admin can create new shopper users and set them to active and inactive.  The view is refreshed after add or status change.  Still have to think more about deleting since there has to be a safety to ensure not all admins are deleted.  Admins can't be created right now either.  So only one default admin.  Some files were renamed because this is getting messy.  I did not consider naming conventions because I didn't know what I needed or how I would build this project.  Also worked on AdminInventory.  Two buttons now move between two fragments in the activity.<br><br>

12/5 - Did I do anything?  Or was this a take a break day?<br><br>

12/6 - Finally have the item database showing on the Admin Item Inventory activity.  I also have toolbar back button working.  Made some progress on the Item edit activity, but not enough to put on GitHub yet.<br><br>

12/7 - Added the Item Detail activity that chains off the Admin Item Inventory activity.  My hope is that both of these activities can be reused in some way for the shopper side.  Almost PASSing half the use cases I have.<br><br>

12/8 - Everything is starting to mix together and I feel like I am getting lost in what is happening.  I often find myself stepping back when I feel overwhelmed so that I can refresh my mind's view of where I am at.  I got a good amount of work done in the Shopper side of the program.  Shoppers can now see the shop inventory and make purchases when they View an item's details.  Shoppers can also see their Order History, but the delete button is not implemented yet.  Made good progress today even though I felt like I was drowning in code.<br><br>

12/9 - Completed Shopper Order Returns and Admin Sales Report.  This means all 16 use cases now pass.  I still need to update some images and double check on everything.  When everything looks good, I will add the image stuff needed on the use case document and record videos for the final project video showing off my program in all its glory.  So glad to have gotten this far.<br><br>

12/10 - Checking for bugs while recording videos for each use case.  I think I will redo the videos and then create one YouTube video of the whole project working.  I think it will be better if I come up with a video script so i can efficiently showcase everything.  I was finding my recordings being repetitious, and that costs time and storage space.
