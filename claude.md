#Slash command
-> claude -r   (show conversation)
-> /resume (we can go in other session)
-> /rename negative margin (give name to session)
-> /btw (using this command (btw->by the way) we can ask question to claude not related to current session also it will not recorded in current session context at last run space so it will be removed)
-> /export file.md (we can export our conversation to use it further)
-> /logout (logout from claude account)
-> /login (if you want to setup again)
-> /model (switch between model)
-> /usage (check usage of current ses sion or week)
-> /extra-usage (if limit reached)
-> /stats (shows stats of using claude)
-> /insights (it gives us html file of how we are using claude code and how we can improve it)
-> /config (to configure claude code like turn on thinking mode)
-> /permissions (to give permission to claude code like webSearch or to run command)
-> /theme (customize claude code theme)
-> /voice (to give voice command press space button tp disable again enter /voice)
-> /context (show which things occupies the context)
-> /compact (summaries the context)
-> /clear (similar of starting as new session)
-> /effort (it can be low medium high xhigh max on max it uses to much token so use it with care)
-> /ultraplan (in this when you want some to some high end task planning with this command it create one instance of opus model on cclaud and do all this there instead of your localmachine and later you can fetch it on your local machine)

---------------------------------------------------------------------------------------
In claude you can give image to claude and claude will give you code of that image using claude multimodel
---------------------------------------------------------------------------------------

As LLMs do not have memory, I always start with a new so to solve this problem, we can create claude.md file 
Either manually or use the command /init, and it will create the whole project file

--------------------------------------------------------------------------------------------
Claude is having claude.md file one for whole ssystem other for project which we commit in git and entier team can use it. 
Also we can create multiple .md files according to our requirement but claude will not fetch them directly in every context.

----------------------------------------------------------------------------------------------
There is a difference in vibe coder and agentic coder....vibe coder just reply on this llm don't know how actually things work and end up in a loop of fixing things.

Agentic coder do spec-driven development
There spec file include
-> problem statement
-> functional Requiremnts
-> Api contracts (input, output, data shapes)
-> Constraints
-> Edge cases and error handling
-> Acceptance criteria

flow of spec-driven development
Spec -> Review -> Design -> Review -> Tasks -> Build -> Validate











