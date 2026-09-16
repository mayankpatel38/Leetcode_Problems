class Solution {
public:
    vector<int> twoSum(vector<int>& numbers, int target) {
      vector<int>ans;
      int start=0, end=numbers.size()-1;
      while(start<end){
        if(numbers[start]+numbers[end]==target)
        {
            start++, end++;
            ans.push_back(start);
            ans.push_back(end);
            break;
        }
        else if(numbers[start]+numbers[end]<target)
        start++;

        else 
        end--;
      } 
      return ans;
    }
};