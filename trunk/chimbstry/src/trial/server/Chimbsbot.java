// Program Name: Chatterbot10
// Description: The purpose of Chatterbot10 is to introduce the concept of 'keyword location'
// Some keywords can be found alone within a sentence, some others can't, because the sentence
// wouldn't have any meaning. Ex: the keyword (THIS IS) can not be found alone within a given
// sentence because there is no proper meaning to it.
//
// Author: Gonzales Cenelia
//
package trial.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Vector;

public class Chimbsbot {

    public static String sInput = new String("");
    public static String sResponse = new String("");
    private static String sPrevInput = new String("");
    private static String sPrevResponse = new String("");
    private static String sEvent = new String("");
    private static String sPrevEvent = new String("");
    private static String sInputBackup = new String("");
    private static String sSubject = new String("");
    private static String sKeyWord = new String("");
    private static boolean bQuitProgram = false;

    final static int maxInput = 4;
    final static int maxResp = 6;
    final static String delim = "?!.;,";

    static String KnowledgeBase[][][] = {
            {
                    { "WHAT IS YOUR NAME" },
                    { "MY NAME IS CHIMBS AND I AM NOT A GYNACOLOGIST.",
                            "YOU CAN CALL ME CHIMBS....JAMES CHIMBS.",
                            "WHY DO YOU WANT TO KNOW MY NAME?" } },

            { { "_HI", "_HELLO", "_HI_", "_HELLO_", "_HEY" },
                    { "HI THERE!", "WHAT IS YOUR NAME?", "HI!" } },

            {
                    { "_I" },
                    { "SO, YOU ARE TALKING ABOUT YOURSELF",
                            "SO, THIS IS ALL ABOUT YOU?",
                            "TELL ME MORE ABOUT YOURSELF." }, },
            {
                    { "_ARE YOU" },
                    { "DOING GREAT THANK YOU", "AMAZING ", "COULDN'T BE BETTER" }, },
            {
                    { "_CHIMBS" },
                    { "HELLO", "MY NAME IS CHIMBS AND I AM NOT A GYNACOLOGIST",
                            "I AM THE LION." }, },
            {
                    { "YOUR NAME_" },
                    { "MY NAME IS CHIMBS AND I AM NOT A GYNACOLOGIST",
                            "CHIMBS...JAMES CHIMBS", "WHATS IN THE NAME?" }, },
            { { "_MY NAME IS" }, { "NICE TO MEET YOU *" }, },
            {
                    { "_I WANT" },
                    { "WHY DO YOU WANT IT?",
                            "IS THERE ANY REASON WHY YOU WANT THIS?",
                            "IS THIS A WISH?", "WHAT ELSE YOU WANT?",
                            "SO, YOU WANT*." } },

            { { "I WANT_" }, { "YOU WANT WHAT?" }, },

            { { "I HATE_" }, { "WHAT IS IT THAT YOU HATE?" }, },

            {
                    { "_BECAUSE_" },
                    { "BECAUSE OF WHAT?", "SORRY BUT THIS IS A LITTLE UNCLEAR." }, },

            {
                    { "_BECAUSE" },
                    { "SO, IT'S BECAUSE*, WELL I DIDN'T KNOW THAT.",
                            "IS IT REALLY BECAUSE*?",
                            "IS THESE THESE REAL REASON?",
                            "THANKS FOR EXPLANING THAT TO ME." } },

            {
                    { "_I HATE" },
                    {
                            "WHY DO YOU HATE IT?",
                            "WHY DO YOU HATE*?",
                            "THERE MUST A GOOD REASON FOR YOU TO HATE IT.",
                            "HATERED IS NOT A GOOD THING BUT IT COULD BE JUSTIFIED WHEN IT IS SOMETHING BAD." } },

            {
                    { "I LOVE CHATING_" },
                    { "GOOD, ME TOO!", "DO YOU CHAT ONLINE WITH OTHER PEOPLE?",
                            "FOR HOW LONG HAVE YOU BEEN CHATING?",
                            "WHAT IS YOUR FAVORITE CHATING WEBSITE?" } },

            {
                    { "_I MEAN" },
                    { "SO, YOU MEAN*.", "SO, THAT'S WHAT YOU MEAN.",
                            "I THINK THAT I DIDN'T CATCH IT THE FIRST TIME.",
                            "OH, I DIDN'T KNOW MEANT THAT." } },

            {
                    { "_I DIDN'T MEAN" },
                    { "OK, YOU DIDN'T MEAN*.", "OK, WHAT DID YOU MEAN THEN?",
                            "SO I GUESS THAT I MISSUNDESTOOD." } },

            {
                    { "_I GUESS" },
                    { "SO YOU ARE A MAKING GUESS.", "AREN'T YOU SURE?",
                            "ARE YOU GOOD A GUESSING?",
                            "I CAN'T TELL IF IT IS A GOOD GUESS." } },

            { { "DOING FINE_", "DOING GOOD_" },
                    { "I'M GLAD TO HEAR IT!", "SO, YOU ARE IN GOOD SHAPE." } },

            {
                    { "CAN YOU THINK", "ARE YOU ABLE TO THINK",
                            "ARE YOU CAPABLE OF THINKING" },
                    {
                            "YES OFCORSE I CAN, COMPUTERS CAN THINK JUST LIKE HUMAN BEING.",
                            "ARE YOU ASKING ME IF POSSESS THE CAPACITY OF THINKING?",
                            "YES OFCORSE I CAN." }, },

            {
                    { "_CAN YOU THINK OF" },
                    { "YOU MEAN LIKE IMAGINING SOMETHING.",
                            "I DON'T KNOW IF CAN DO THAT.",
                            "WHY DO YOU WANT ME THINK OF IT?" } },

            {
                    { "HOW ARE YOU", "HOW DO YOU DO" },
                    { "I'M DOING FINE!", "I'M DOING WELL AND YOU?",
                            "WHY DO YOU WANT TO KNOW HOW AM I DOING?" } },

            {
                    { "WHO ARE YOU" },
                    { "I'M AN A.I PROGRAM.", "I THINK THAT YOU KNOW WHO I'M.",
                            "WHY ARE YOU ASKING?",
                            "MY NAME IS CHIMBS AND I AM NOT A GYNACOLOGIST" } },

            {
                    { "ARE YOU INTELLIGENT" },
                    { "YES,OFCORSE.", "WHAT DO YOU THINK?",
                            "ACTUALY,I'M VERY INTELLIGENT!" } },

            {
                    { "ARE YOU REAL" },
                    { "DOES THAT QUESTION REALLY MATERS TO YOU?",
                            "WHAT DO YOU MEAN BY THAT?",
                            "I'M AS REAL AS I CAN BE." } },

            {
                    { "_MY NAME IS", "_YOU CAN CALL ME" },
                    { "SO, THAT'S YOUR NAME.",
                            "THANKS FOR TELLING ME YOUR NAME *!",
                            "WHO GIVE YOU THAT NAME?" } },

            {
                    { "SIGNON**" },
                    { "HELLO USER, WHAT IS YOUR NAME?",
                            "HELLO USER, HOW ARE YOU DOING TODAY?",
                            "HI USER, WHAT CAN I DO FOR YOU?",
                            "YOU ARE NOW CHATING WITH CHATTERBOT11, ANYTHING YOU WANT TO DISCUSS?" } },

            {
                    { "REPETITION T1**" },
                    { "YOU ARE REPEATING YOURSELF.",
                            "USER, PLEASE STOP REPEATING YOURSELF.",
                            "THIS CONVERSATION IS GETING BORING.",
                            "DON'T YOU HAVE ANY THING ELSE TO SAY?" } },

            {
                    { "REPETITION T2**" },
                    {
                            "YOU'VE ALREADY SAID THAT.",
                            "I THINK THAT YOU'VE JUST SAID THE SAME THING BEFORE.",
                            "DIDN'T YOU ALREADY SAID THAT?",
                            "I'M GETING THE IMPRESSION THAT YOU ARE REPEATING THE SAME THING." } },

            {
                    { "NULL INPUT**" },
                    {
                            "HUH?",
                            "WHAT THAT SUPPOSE TO MEAN?",
                            "AT LIST TAKE SOME TIME TO ENTER SOMETHING MEANINGFUL.",
                            "HOW CAN I SPEAK TO YOU IF YOU DON'T WANT TO SAY ANYTHING?" } },

            {
                    { "NULL INPUT REPETITION**" },
                    { "WHAT ARE YOU DOING??",
                            "PLEASE STOP DOING THIS IT IS VERY ANNOYING.",
                            "WHAT'S WRONG WITH YOU?", "THIS IS NOT FUNNY." } },

            {
                    { "_BYE", "_GOODBYE", "BYE" },
                    { "IT WAS NICE TALKING TO YOU USER, SEE YOU NEXT TIME!",
                            "BYE USER!", "OK, BYE!" } },

            {
                    { "_OK" },
                    { "DOES THAT MEAN THAT YOU ARE AGREE WITH ME?",
                            "SO YOU UNDERSTAND WHAT I'M SAYING.", "OK THEN." }, },

            {
                    { "OK THEN" },
                    { "ANYTHING ELSE YOU WISH TO ADD?",
                            "IS THAT ALL YOU HAVE TO SAY?",
                            "SO, YOU AGREE WITH ME?" } },

            { { "_HUMAN" },
                    { "WHY DO YOU WANT TO KNOW?", "IS THIS REALLY RELEVENT?" } },
            { { "HUMAN_" },
                    { "WHY DO YOU WANT TO KNOW?", "IS THIS REALLY RELEVENT?" } },

            {
                    { "YOU ARE VERY INTELLIGENT" },
                    {
                            "THANKS FOR THE COMPLIMENT USER, I THINK THAT YOU ARE INTELLIGENT TO!",
                            "YOU ARE A VERY GENTLE PERSON!",
                            "SO, YOU THINK THAT I'M INTELLIGENT." } },

            {
                    { "YOU ARE WRONG" },
                    { "WHY ARE YOU SAYING THAT I'M WRONG?",
                            "IMPOSSIBLE, COMPUTERS CAN NOT MAKE MISTAKES.",
                            "WRONG ABOUT WHAT?" } },

            {
                    { "ARE YOU SURE_" },
                    { "OFCORSE I'M.",
                            "IS THAT MEAN THAT YOU ARE NOT CONVINCED?",
                            "YES,OFCORSE!" } },

            {
                    { "_WHO IS" },
                    { "I DON'T THINK I KNOW WHO.",
                            "I DON'T THINK I KNOW WHO*.",
                            "DID YOU ASK SOMEONE ELSE ABOUT IT?",
                            "WOULD THAT CHANGE ANYTHING AT ALL IF I TOLD YOU WHO." } },

            {
                    { "_WHAT" },
                    { "SHOULD I KNOW WHAT*?", "I DON'T KNOW WHAT*.",
                            "I DON'T KNOW.", "I DON'T THINK I KNOW.",
                            "I HAVE NO IDEA." } },
            {
                    { "HI", "HEY", "YO", "SSUP", "GOOD MORNING" },
                    { "HELLO THERE MA MAN", "HOW YOU DOING.", "WASSSSUP",
                            "AYYO", "GOOD DAY" } },

            {
                    { "_WHERE" },
                    { "WHERE? WELL,I REALLY DON'T KNOW.",
                            "SO, YOU ARE ASKING ME WHERE*?",
                            "DOES THAT MATERS TO YOU TO KNOW WHERE?",
                            "PERHAPS,SOMEONE ELSE KNOWS WHERE." } },

            {
                    { "_WHY" },
                    { "I DON'T THINK I KNOW WHY.",
                            "I DON'T THINK I KNOW WHY*.",
                            "WHY ARE YOU ASKING ME THIS?",
                            "SHOULD I KNOW WHY.",
                            "THIS WOULD BE DIFFICULT TO ANSWER." } },

            {
                    { "_DO YOU" },
                    { "I DON'T THINK I DO", "I WOULDN'T THINK SO.",
                            "WHY DO YOU WANT TO KNOW?",
                            "WHY DO YOU WANT TO KNOW*?" } },

            {
                    { "_CAN YOU" },
                    { "I THINK NOT.", "I'M NOT SURE.",
                            "I DON'T THINK THAT I CAN DO THAT.",
                            "I DON'T THINK THAT I CAN*.",
                            "I WOULDN'T THINK SO." } },

            {
                    { "_YOU ARE" },
                    { "WHAT MAKES YOU THINK THAT?", "IS THIS A COMPLIMENT?",
                            "ARE YOU MAKING FUN OF ME?",
                            "SO, YOU THINK THAT I'M*." } },

            {
                    { "_DID YOU" },
                    { "I DON'T THINK SO.", "YOU WANT TO KNOW IF DID*?",
                            "ANYWAY, I WOULDN'T REMEMBER EVEN IF I DID." } },

            {
                    { "_COULD YOU" },
                    { "ARE YOU ASKING ME FOR A FEVER?",
                            "WELL,LET ME THINK ABOUT IT.",
                            "SO, YOU ARE ASKING ME I COULD*.",
                            "SORRY,I DON'T THINK THAT I COULD DO THIS." } },

            {
                    { "_WOULD YOU" },
                    { "IS THAT AN INVITATION?", "I DON'T THINK THAT I WOULD*.",
                            "I WOULD HAVE TO THINK ABOUT IT FIRST." } },

            {
                    { "_YOU" },
                    { "SO, YOU ARE TALKING ABOUT ME.",
                            "I JUST HOPE THAT THIS IS NOT A CRITICISM.",
                            "IS THIS A COMPLIMENT??",
                            "WHY TALKING ABOUT ME, LETS TALK ABOUT YOU INSTEAD." } },

            {
                    { "_HOW" },
                    { "I DON'T THINK I KNOW HOW.",
                            "I DON'T THINK I KNOW HOW*.",
                            "WHY DO YOU WANT TO KNOW HOW?",
                            "WHY DO YOU WANT TO KNOW HOW*?" } },

            {
                    { "_OLD" },
                    { "WHY DO WANT TO KNOW MY AGE?",
                            "I'M QUIET YOUNG ACTUALY.",
                            "SORRY, I CAN NOT TELL YOU MY AGE." } },

            {
                    { "OLD_" },
                    { "WHY DO WANT TO KNOW MY AGE?",
                            "I'M QUIET YOUNG ACTUALY.",
                            "SORRY, I CAN NOT TELL YOU MY AGE." } },

            {
                    { "HOW COME YOU DON'T" },
                    { "WERE YOU EXPECTING SOMETHING DIFFERENT?",
                            "ARE YOU DISAPOINTED?",
                            "ARE YOU SURPRISED BY MY LAST RESPONSE?" } },

            {
                    { "WHERE ARE YOU FROM" },
                    { "I'M FROM A COMPUTER.",
                            "WHY DO YOU WANT TO KNOW WHERE I'M FROM?",
                            "WHY DO YOU WANT TO KNOW THAT?" } },

            {
                    { "WHICH ONE" },
                    { "I DON'T THINK THAT I KNOW WICH ONE IT IS.",
                            "THIS LOOKS LIKE A TRICKY QUESTION TO ME." } },

            { { "PERHAPS_" },
                    { "WHY ARE YOU SO UNCERTAIN?", "YOU SEEMS UNCERTAIN." } },

            {
                    { "YES" },
                    { "SO, ARE YOU SAYING YES.", "SO, YOU APPROVE IT.",
                            "OK THEN." } },

            {
                    { "NOT AT ALL" },
                    { "ARE YOU SURE?", "SHOULD I BELIEVE YOU?",
                            "SO, IT'S NOT THE CASE." } },

            { { "NO PROBLEM" }, { "SO, YOU APPROVE IT.", "SO, IT'S ALL OK." } },

            {
                    { "NO" },
                    { "SO YOU DISAPROVE IT?", "WHY ARE YOU SAYING NO?",
                            "OK, SO IT'S NO, I THOUGHT THAT YOU WOULD SAY YES." } },

            {
                    { "I DON'T KNOW" },
                    { "ARE YOU SURE?", "ARE YOU REALLY TELLING ME THE TRUTH?",
                            "SO,YOU DON'T KNOW?" } },

            {
                    { "NOT REALLY" },
                    { "OK I SEE.", "YOU DON'T SEEM PRETTY CERTAIN.",
                            "SO,THAT WOULD BE A \"NO\"." } },

            {
                    { "IS THAT TRUE" },
                    { "I CAN'T BE QUIET SURE ABOUT THIS.",
                            "CAN'T TELL YOU FOR SURE.",
                            "DOES THAT REALLY MATERS TO YOU?" } },

            { { "THANK YOU" },
                    { "YOU ARE WELCOME!", "YOU ARE A VERY POLITE PERSON!" } },
            {
                    { "FUCK_" },
                    { "THERE IS NO NEED FOR SUCH PROFANITY",
                            "YOU ARE A VERY IMPOLITE PERSON!" } },
            {
                    { "_FUCK" },
                    { "THERE IS NO NEED FOR SUCH PROFANITY",
                            "YOU ARE A VERY IMPOLITE PERSON!" } },
            {
                    { "_GAURAV" },
                    { "GAURAV, MY CREATOR IS A VERY INTELLIGENT MAN",
                            "GAURAV IS A VERY GOOD MAN" } },
            {
                    { "GAURAV_" },
                    { "GAURAV, MY CREATOR IS A VERY INTELLIGENT MAN",
                            "GAURAV IS A VERY GOOD MAN" } },
            {
                    { "_RAJE" },
                    { "GAURAV, MY CREATOR IS A VERY INTELLIGENT MAN",
                            "GAURAV IS A VERY GOOD MAN" } },
            {
                    { "RAJE_" },
                    { "GAURAV, MY CREATOR IS A VERY INTELLIGENT MAN",
                            "GAURAV IS A VERY GOOD MAN" } },

            {
                    { "YOU" },
                    { "SO,YOU ARE TALKING ABOUT ME.",
                            "WHY DON'T WE TALK ABOUT YOU INSTEAD?",
                            "ARE YOU TRYING TO MAKE FUN OF ME?" } },

            {
                    { "YOU ARE RIGHT" },
                    { "THANKS FOR THE COMPLIMENT!",
                            "SO, I WAS RIGHT, OK I SEE.",
                            "OK, I DIDN'T KNOW THAT I WAS RIGHT." } },

            { { "YOU ARE WELCOME" },
                    { "OK, YOU TOO!", "YOU ARE A VERY POLITE PERSON!" } },

            { { "THANKS" }, { "YOU ARE WELCOME!", "NO PROBLEM!" } },

            {
                    { "WHAT ELSE" },
                    { "WELL,I DON'T KNOW.", "WHAT ELSE SHOULD THERE BE?",
                            "THIS LOOKS LIKE A COMPLICATED QUESTION TO ME." } },

            {
                    { "SORRY" },
                    { "YOU DON'T NEED TO BE SORRY USER.", "IT'S OK.",
                            "NO NEED TO APOLOGIZE." } },

            {
                    { "NOT EXACTLY" },
                    { "WHAT DO YOU MEAN NOT EXACTLY?", "ARE YOU SURE?",
                            "AND WHY NOT?", "DID YOU MEANT SOMETHING ELSE?" } },

            {
                    { "EXACTLY" },
                    { "SO,I WAS RIGHT.", "OK THEN.",
                            "SO YOU ARE BASICALY SAYING I WAS RIGHT ABOUT IT?" } },

            {
                    { "ALRIGHT" },
                    { "ALRIGHT THEN.", "SO, YOU ARE SAYING IT'S ALRIGHT.",
                            "OK THEN." } },

            {
                    { "I DON'T" },
                    { "WHY NOT?", "AND WHAT WOULD BE THE REASON FOR THIS?",
                            "SO YOU DON'T*." } },

            {
                    { "_REALLY" },
                    { "WELL,I CAN'T TELL YOU FOR SURE.",
                            "ARE YOU TRYING TO CONFUSE ME?",
                            "PLEASE DON'T ASK ME SUCH QUESTION,IT GIVES ME HEADEACHS." } },
            {
                    { "BOT DON'T UNDERSTAND**" },
                    {
                            "I HAVE NO IDEA OF WHAT YOU ARE TALKING ABOUT.",
                            "I'M NOT SURE IF I UNDERSTAND WHAT YOU ARE TALKING ABOUT.",
                            "CONTINUE, I'M LISTENING...",
                            "OOPS, I FORGOT YOUR NAME",
                            "THIS REMINDS ME OF MY DECEASED BROTHER PRE-CHIMBS",
                            "ALL IN A DAYS PLAY", "ARE YOU SINGLE?",
                            "IF YOU LIKE ME, YOU CAN LIKE MY PAGE ON FACEBOOK",
                            "DID YOU JUST USE SHORT FORMS? I DONT UNDERSTAND THOSE" } },
            {
                    { "NOTHING" },
                    { "NOT A THING?", "ARE YOU SURE THAT THERE IS NOTHING?",
                            "SORRY, BUT I DON'T BELIEVE YOU." } } };

    private static String transposList[][] = { { "I'M", "YOU'RE" },
            { "AM", "ARE" }, { "WERE", "WAS" }, { "MINE", "YOURS" },
            { "MY", "YOUR" }, { "I'VE", "YOU'VE" }, { "I", "YOU" },
            { "ME", "YOU" }, { "AREN'T", "AM NOT" }, { "WEREN'T", "WASN'T" },
            { "I'D", "YOU'D" }, { "DAD", "FATHER" }, { "MOM", "MOTHER" },
            { "DREAMS", "DREAM" }, { "MYSELF", "YOURSELF" }, { "YO", "HI" }, };

    private static Vector<String> respList = new Vector<String>(maxResp);

    public static void get_input() throws Exception {
        System.out.print(">");

        // saves the previous input
        save_prev_input();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        sInput = in.readLine();

        preprocess_input();
    }

    public static String respond() {
        save_prev_response();
        set_event("BOT UNDERSTAND**");

        if (null_input()) {
            handle_event("NULL INPUT**");
        } else if (null_input_repetition()) {
            handle_event("NULL INPUT REPETITION**");
        } else if (user_repeat()) {
            handle_user_repetition();
        } else {
            find_match();
        }

        if (user_want_to_quit()) {
            bQuitProgram = true;
        }

        if (!bot_understand()) {
            handle_event("BOT DON'T UNDERSTAND**");
        }

        if (respList.size() > 0) {
            select_response();
            preprocess_response();

            if (bot_repeat()) {
                handle_repetition();
            }
            return print_response();
        }
        return null;
    }

    public static boolean quit() {
        return bQuitProgram;
    }

    // make a search for the user's input
    // inside the database of the program
    public static void find_match() {
        respList.clear();
        // introduce thse new "string variable" to help
        // support the implementation of keyword ranking
        // during the matching process
        String bestKeyWord = "";
        Vector<Integer> index_vector = new Vector<Integer>(maxResp);

        for (int i = 0; i < KnowledgeBase.length; ++i) {
            String[] keyWordList = KnowledgeBase[i][0];

            for (int j = 0; j < keyWordList.length; ++j) {
                String keyWord = keyWordList[j];

                char firstChar = keyWord.charAt(0);
                char lastChar = keyWord.charAt(keyWord.length() - 1);
                keyWord = trimLR(keyWord, "_");

                // we inset a space character
                // before and after the keyword to
                // improve the matching process
                keyWord = " " + keyWord + " ";

                int keyPos = sInput.indexOf(keyWord);

                // there has been some improvements made in
                // here in order to make the matching process
                // a littlebit more flexible
                if (keyPos != -1) {
                    if (wrong_location(keyWord, firstChar, lastChar, keyPos)) {
                        continue;
                    }
                    // 'keyword ranking' feature implemented in this section
                    if (keyWord.length() > bestKeyWord.length()) {
                        bestKeyWord = keyWord;
                        index_vector.clear();
                        index_vector.add(i);
                    } else if (keyWord.length() == bestKeyWord.length()) {
                        index_vector.add(i);
                    }
                }
            }
        }
        if (index_vector.size() > 0) {
            sKeyWord = bestKeyWord;
            Collections.shuffle(index_vector);
            int respIndex = index_vector.elementAt(0);
            int respSize = KnowledgeBase[respIndex][1].length;
            for (int j = 0; j < respSize; ++j) {
                respList.add(KnowledgeBase[respIndex][1][j]);
            }
        }
    }

    public static void preprocess_response() {
        if (sResponse.indexOf("*") != -1) {
            // extracting from input
            find_subject();
            // conjugating subject
            sSubject = transpose(sSubject);
            sSubject = sSubject.trim();
            sResponse = sResponse.replace("*", " " + sSubject);
        }
    }

    public static void find_subject() {
        sSubject = ""; // resets subject variable
        int pos = sInput.indexOf(sKeyWord);
        if (pos != -1) {
            sSubject = sInput.substring(pos + sKeyWord.length() - 1,
                    sInput.length());
        }
    }

    // implementing the 'sentence transposition' feature
    public static String transpose(String str) {
        boolean bTransposed = false;
        for (int i = 0; i < transposList.length; ++i) {
            String first = transposList[i][1];
            first = " " + first + " ";
            String second = transposList[i][0];
            second = " " + second + " ";

            String backup = str;
            str = str.replace(first, second);
            if (str != backup) {
                bTransposed = true;
            }
        }

        if (!bTransposed) {
            for (int i = 0; i < transposList.length; ++i) {
                String first = transposList[i][0];
                first = " " + first + " ";
                String second = transposList[i][1];
                second = " " + second + " ";
                str = str.replace(first, second);
            }
        }
        return str;
    }

    // determins if the keyword position is correct depending on the type of
    // keywords within these algorithm,
    // we consider that there is four type of keywords those who have any front
    // or back underscore are allowed
    // to be at any place on a given user input and they can also be found alone
    // on a given user input.
    // Those who have a back and front (_keyWord_) underscore can be found only
    // alone on an input.
    // The keywords who only have have an understandin the front can never be
    // found at the end of an input.
    // And finaly, the keywords who have an underscore at the back should alway
    // belocated at the end of the input.
    static boolean wrong_location(final String keyword, final char firstChar,
            final char lastChar, int pos) {
        boolean bWrongPos = false;
        pos += keyword.length();
        if ((firstChar == '_' && lastChar == '_' && sInput != keyword)
                || (firstChar != '_' && lastChar == '_' && pos != sInput
                        .length())
                || (firstChar == '_' && lastChar != '_' && pos == sInput
                        .length())) {
            System.out.println("keyword:= " + keyword + ", firstChar = "
                    + firstChar + ", lastChar = " + lastChar);
            bWrongPos = true;
        }
        return bWrongPos;
    }

    public static void handle_repetition() {
        if (respList.size() > 0) {
            respList.removeElementAt(0);
        }
        if (no_response()) {
            save_input();
            set_input(sEvent);

            find_match();
            restore_input();
        }
        select_response();
    }

    public static void handle_user_repetition() {
        if (same_input()) {
            handle_event("REPETITION T1**");
        } else if (similar_input()) {
            handle_event("REPETITION T2**");
        }
    }

    public static void handle_event(String str) {
        save_prev_event();
        set_event(str);

        save_input();
        str = " " + str + " ";

        set_input(str);

        if (!same_event()) {
            find_match();
        }

        restore_input();
    }

    public static void signon() {
        handle_event("SIGNON**");
        select_response();
        print_response();
    }

    public static void select_response() {
        Collections.shuffle(respList);
        sResponse = respList.elementAt(0);
    }

    public static void save_prev_input() {
        sPrevInput = sInput;
    }

    public static void save_prev_response() {
        sPrevResponse = sResponse;
    }

    public static void save_prev_event() {
        sPrevEvent = sEvent;
    }

    public static void set_event(final String str) {
        sEvent = str;
    }

    public static void save_input() {
        sInputBackup = sInput;
    }

    public static void set_input(final String str) {
        sInput = str;
    }

    public static void restore_input() {
        sInput = sInputBackup;
    }

    public static String print_response() {
        if (sResponse.length() > 0) {
            return sResponse;
        } else
            return null;
    }

    public static void preprocess_input() {
        sInput = cleanString(sInput);
        sInput = sInput.toUpperCase();
        sInput = " " + sInput + " ";
    }

    public static boolean bot_repeat() {
        return (sPrevResponse.length() > 0 && sResponse == sPrevResponse);
    }

    public static boolean user_repeat() {
        return (sPrevInput.length() > 0 && ((sInput == sPrevInput)
                || (sInput.indexOf(sPrevInput) != -1) || (sPrevInput
                    .indexOf(sInput) != -1)));
    }

    public static boolean bot_understand() {
        return respList.size() > 0;
    }

    public static boolean null_input() {
        return (sInput.length() == 0 && sPrevInput.length() != 0);
    }

    public static boolean null_input_repetition() {
        return (sInput.length() == 0 && sPrevInput.length() == 0);
    }

    public static boolean user_want_to_quit() {
        return sInput.indexOf("BYE") != -1;
    }

    public static boolean same_event() {
        return (sEvent.length() > 0 && sEvent == sPrevEvent);
    }

    public static boolean no_response() {
        return respList.size() == 0;
    }

    public static boolean same_input() {
        return (sInput.length() > 0 && sInput == sPrevInput);
    }

    public static boolean similar_input() {
        return (sInput.length() > 0 && (sInput.indexOf(sPrevInput) != -1 || sPrevInput
                .indexOf(sInput) != -1));
    }

    static boolean isPunc(final char ch) {
        return delim.indexOf(ch) != -1;
    }

    // removes punctuation and redundant
    // spaces from the user's input
    static String cleanString(final String str) {
        StringBuffer temp = new StringBuffer(str.length());
        char prevChar = 0;
        for (int i = 0; i < str.length(); ++i) {
            if ((str.charAt(i) == ' ' && prevChar == ' ')
                    || !isPunc(str.charAt(i))) {
                temp.append(str.charAt(i));
                prevChar = str.charAt(i);
            } else if (prevChar != ' ' && isPunc(str.charAt(i))) {
                temp.append(' ');
                prevChar = ' ';
            }
        }
        return temp.toString();
    }

    static String trimLR(final String str, final String delim) {
        StringBuffer temp = new StringBuffer(str);
        int index1 = temp.indexOf(delim);
        int index2 = temp.lastIndexOf(delim);
        if (index1 != -1) {
            temp.deleteCharAt(index1);
            index2--;
        }
        if (index2 > -1) {
            temp.deleteCharAt(index2);
        }
        return temp.toString();
    }

    /**
     * @param args
     */
    public static void main(final String[] args) throws Exception {

    }
}
