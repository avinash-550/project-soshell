# Release I
## User Authentication and Authorization
 - Allow users to create accounts, log in, and log out.
 - Secure password storage and authentication.

```
user signup --username <username> --password <password>
user login --username <username> --password <password>
user view <username>
user logout
user delete <username>
```

## Posting and Sharing
 - Allows users to create text-based posts.
 - Ability to share and rate(1-10) posts.

```
post create <post-content>
post update <post-id> <updated-post-content>
post delete <post-id>
post rate <post-id> <rating-point> # rating point between 1-10
post view <post-id>
post view # returns list of user created posts
post view --limit <int(1-5), min=1 default,max=5> --page <int(1-n), min=1 default=1> # get user posts in paginated way
feed # returns top 5 unread posts and mark them as 'read'
feed --limit <int(1-5), min=1 default,max=5> --page <int(1-n), min=1 default=1># get feed in paginated way
```


Phase 2
## Feed and Following
 - Allow users to follow and unfollow other users.
 - Feed of posts from users the current user follows.
 - Display a list of followers and following. 

```
user follow <username>
user unfollow <username>
feed # returns top 5 unread posts and mark them as 'read'
feed --limit <int(1-5), min=1 default,max=5> --page <int(1-n), min=1 default=1># get feed in paginated way
user following # returns top 5 users user is following
user following --limit <int(1-5), min=1 default,max=5> --page <int(1-n), min=1 default=1> # get following in paginated way
user followers # retunrs top 5 users following user
user followers --limit <int(1-5), min=1 default,max=5> --page <int(1-n), min=1 default=1> # get followers in paginated way
```

## Profile Update and Delete
 - Store user account information and settings. 
 - Allow users to update account information, privacy settings, etc.
```
user update # returns a key-value pair of settings that can be updated
user update --setting-name <setting-name> --setting-val <setting-value> # updates specified setting for user
user delete # returns a delete token
user delete <delete-token> # permanently deletes the user
user delete --force <username> # direct delete
```

Phase 3
## Search Functionality
 - Search a user or post by given keyword.

```
search users abc # searches user(s) with username containing abc
search posts abc # searches posts(s) containing xyz under all users
search posts --user-id abc xyz # searches for post(s) containing xyz under user abc
search <entity(users/posts), default=posts> --user-id <user-id> --limit <int(1-5), min=1 default,max=5> --page <int(1-n), min=1 default=1> <anyKeyword(string), default="" minlen=0 maxlen=10> # full search command
```