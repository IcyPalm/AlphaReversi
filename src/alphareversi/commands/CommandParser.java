package alphareversi.commands;

import alphareversi.commands.receive.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by timmein on 24/03/16.
 */
public class CommandParser {
    private static final Map<String, Class> CommandIdentifiers = new HashMap<String, Class>() {{
        put("SVR GAME CHALLENGE CANCELLED \\{(.*?)\\}", RecvGameChallengeCanceledCommand.class);
        put("SVR GAME CHALLENGE \\{(.*?)\\}", RecvGameChallengeCommand.class);
        put("SVR GAME MATCH \\{(.*?)\\}", RecvGameMatchCommand.class);
        put("SVR GAMELIST \\[(.*?)\\]", RecvGamelistCommand.class);
        put("SVR GAME MOVE \\{(.*?)\\}", RecvGameMoveCommand.class);
        put("SVR GAME (WIN|DRAW|LOSS) \\{(.*?)\\}", RecvGameResultCommand.class);
        put("SVR GAME YOURTURN \\{(.*?)\\}", RecvGameYourturnCommand.class);
        put("SVR PLAYERLIST \\[(.*?)\\]", RecvPlayerlistCommand.class);
        put("SVR HELP (.*?)+", RecvHelpCommand.class);
        put("ERR (.*?)+", RecvStatusErrCommand.class);
        put("OK", RecvStatusOkCommand.class);
    }};

    public static RecvCommand parseString(String serverCommand) {
        for(Map.Entry<String, Class> entry : CommandIdentifiers.entrySet()) {
            String regex = entry.getKey();
            Class commandClass = entry.getValue();
            if(serverCommand.matches(regex))
            {
                try {
                    Constructor constructor = commandClass.getConstructor(String.class);
                    return (RecvCommand)constructor.newInstance(serverCommand);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Unknown command received: " +serverCommand);
        return null;
    }

    public static String[] trimStringArray(String[] stringArray)
    {
        String[] trimmedArray = new String[stringArray.length];
        for (int i = 0; i < stringArray.length; i++)
            trimmedArray[i] = stringArray[i].trim();
        return trimmedArray;
    }

    public static ArrayList parseArraylist(String string)
    {
        ArrayList<String> arrayList = new ArrayList<>();
        string = string.substring(1, string.length() -1);
        String[] parts = CommandParser.trimStringArray(string.split(","));
        for (String part : parts) {
            arrayList.add(part.substring(1, part.length() - 1));
        }
        return arrayList;
    }

    public static HashMap parseObjectMap(String string)
    {
        HashMap<String, String> ObjectMap = new HashMap<>();
        string = string.substring(1, string.length() -1);
        String[] parts = CommandParser.trimStringArray(string.split(","));
        for (String part : parts) {
            String[] parts_second = CommandParser.trimStringArray(part.split(":"));
            ObjectMap.put(parts_second[0], parts_second[1].substring(1, parts_second[1].length() -1));
        }
        return ObjectMap;
    }
}
