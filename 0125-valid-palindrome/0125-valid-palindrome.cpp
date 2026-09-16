class Solution {
public:
    bool isPalindrome(string s) {
        string ch;
        string rev;
        for(int i = 0; i < s.length(); i++){
            if(isalnum(s[i])){
                ch += tolower(s[i]);
            }
           

        }
        
        int start=0, end=ch.length()-1;

        while(start<end)
        {
            if(ch[start]==ch[end])
            {
                start++;
                end--;
            }

            else
            return false;
        }
        return true;
    }
};