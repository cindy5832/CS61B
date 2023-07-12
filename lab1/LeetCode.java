public class LeetCode {
    public static void main(String[] args) {
        String s = "babbabbabbabbab";
        System.out.println(repeatedSubstringPattern(s));
//        System.out.println(s.substring(0, 3));
    }

    public static boolean repeatedSubstringPattern(String s) {
        int l = s.length();
        for (int i = l / 2; i > 0; i--) {
            String s1 = s.substring(0, i);
            StringBuilder st = new StringBuilder();
            for (int j = 1; j <= l / i; j++) {
                st.append(s1);
            }
            if (st.toString().equals(s)) return true;
        }
        return false;
    }
}
