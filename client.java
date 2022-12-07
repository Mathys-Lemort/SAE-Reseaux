// Client
// Le client se connecte au serveur en précisant l’adresse IP ou le nom, ainsi que son nom d’utili-
// sateur. Il voit ensuite la liste des différents salons de discussions. Un salon est un groupe d’utili-
// sateurs pouvant discuter entre-eux. Si son nom d’utilisateur est déjà pris un nouveau nom lui est
// demandé.
// Il choisit ensuite, un groupe d’utilisateurs, qu’il souhaite rejoindre et peut commencer ensuite à
// discuter.
// Il peut envoyer des messages et en parallèle recevoir les messages envoyés sur ce groupe. Sur
// chaque message on peut voir en plus du contenu, le nom de l’expéditeur, et la date/heure.
// Un utilisateur doit pouvoir se déconnecter d’un salon en envoyant la chaîne de caractères /quit
// au serveur. Il peut ensuite se reconnecter à un autre salon, ou se déconnecter en saisissant de
// nouveau /quit.
// Le client peut envoyer des messages au serveur pour lui demander des infos, comme par exemple
// le nombre d’utilisateurs connectés au salon (/nbuser), depuis combien de temps le salon est
// ouvert (uptime), la liste des utilisateurs connectés (/users). Ces requêtes s’insèrent dans le
// flux des messages de l’utilisateur, mais ne sont pas visibles des autres.
// Il doit être possible d’envoyer un message privé à un utilisateur en ajoutant son nom (@<nomUtilisateur>)
// au début du message. Lorsqu’on reçoit un message privé, celui-ci s’insère dans le flux des mes-
// sages du salon, mais il doit être possible de le distinguer des autres messages.
// Cette application client est utilisable dans un terminal.
public class Client