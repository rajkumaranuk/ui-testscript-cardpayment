Feature:
  As a user, I want to do the card payment

Scenario: Card payment and 3d secure authorisation
  Given iFrame url is retrieved by setup
  When card payment page is loaded
  When user enter the payment card details
  Then payment reference is retrieved
  Then query the payment details
  Then enrol for 3d secure payment
  Then authorise 3d secure payment

