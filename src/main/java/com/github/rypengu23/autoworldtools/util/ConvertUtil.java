package com.github.rypengu23.autoworldtools.util;

public class ConvertUtil {

    public String placeholderUtil(String beforeReplaceWord, String afterReplaceWord, String message){

        if(beforeReplaceWord != null && afterReplaceWord != null && message != null) {
            return message.replace(beforeReplaceWord, afterReplaceWord);
        }else{
            return null;
        }
    }

    public String placeholderUtil(String beforeReplaceWord1, String afterReplaceWord1, String beforeReplaceWord2, String afterReplaceWord2, String message){

        message = placeholderUtil(beforeReplaceWord1, afterReplaceWord1, message);
        return placeholderUtil(beforeReplaceWord2, afterReplaceWord2, message);
    }

    public String placeholderUtil(String beforeReplaceWord1, String afterReplaceWord1, String beforeReplaceWord2, String afterReplaceWord2, String beforeReplaceWord3, String afterReplaceWord3, String message){

        message = placeholderUtil(beforeReplaceWord1, afterReplaceWord1, message);
        message = placeholderUtil(beforeReplaceWord2, afterReplaceWord2, message);
        return placeholderUtil(beforeReplaceWord3, afterReplaceWord3, message);
    }

    public String convertColorCode(String word){

        String[] codeListBefore = {"&0","&1","&2","&3","&4","&5","&6","&7","&8","&9","&a","&b","&c","&d","&e","&f","&k","&l","&m","&n","&o","&r"};
        String[] codeListAfter = {"§0","§1","§2","§3","§4","§5","§6","§7","§8","§9","§a","§b","§c","§d","§e","§f","§k","§l","§m","§n","§o","§r"};

        for(int i=0; i<codeListBefore.length; i++){
            word = placeholderUtil(codeListBefore[i], codeListAfter[i], word);
        }
        return word;
    }
}
