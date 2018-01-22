*This is an assignment to generate dummy questions such as "Question0", "Quesion1" etc with different difficulty levels when the user enters 
the total mark for the exam.

This exercise is to demo second level caching (using ehcache), hence I've made the following simplistic assumptions.
1)The total mark is in multiples of 10. That is, the exam is marked out of 30, 40 etc.
2)There are only three difficulty levels. Section 1 Questions (level=1) all weigh 2 marks each, section 2 questions weigh 3, and section 3 questions each weigh 5.
3)Once the user enters the total, the application decides how many questions per section to generate (say N) and selects the first N number of question per each
  difficulty level to be included in the corresponding section.
  
  Expected behavior
  =================
  
Because a new session is opened and closed every time a new set of exam questions are generated, in the first instance of generating exam questions, questions and authors details are fetched from the detabase and saved  to the cache memory. Afterwards, the application gets those details from the cache memory. 
  
You can check by commenting out cache annotations in Question and Author classes to check the case when the ehcache is not used. 
