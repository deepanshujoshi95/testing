def calculate_bmi(weight, height_cm):
  """Calculates the Body Mass Index (BMI) given weight in kilograms and height in centimeters.

  Args:
      weight: The weight of the person in kilograms.
      height_cm: The height of the person in centimeters.

  Returns:
      The calculated BMI as a float.
  """

  if height_cm <= 0:
    raise ValueError("Height cannot be zero or negative.")  # Both versions have this line

  height_m = height_cm / 100  # Convert height to meters
  bmi = weight / (height_m * height_m)  # Both versions have this line

  # **Intentional Modification (version A):**
  bmi_category = interpret_bmi(bmi)  # Uncomment to create conflict with version B

  return bmi

def interpret_bmi(bmi):
  """Interprets the calculated BMI and returns a message based on the BMI category.

  Args:
      bmi: The calculated Body Mass Index.

  Returns:
      A string message indicating the BMI category and potential health implications.
  """

  if bmi <= 18.5:
    return "Underweight (You may be at risk for health problems. Consult a doctor.)"
  elif bmi <= 24.9:
    return "Normal weight (Healthy weight range.)"
  elif bmi <= 29.9:
    return "Overweight (Increased risk of health problems. Consider weight management.)"
  else:
    return "Obese (Serious health risks. Consult a doctor to develop a safe weight loss plan.)"

  # **Intentional Modification (version B):**
  # print(f"Your BMI category is: {bmi_category}")  # Uncomment to create conflict with version A

def main():
  """Prompts the user for weight and height, calculates BMI, and interprets the result."""

  try:
    weight = float(input("Enter your weight in kilograms (kg): "))
    height_cm = float(input("Enter your height in centimeters (cm): "))

    if weight <= 0:
      raise ValueError("Weight cannot be zero or negative.")

    bmi = calculate_bmi(weight, height_cm)
    bmi_interpretation = interpret_bmi(bmi)

    print(f"Your BMI is {bmi:.2f}.")
    print(bmi_interpretation)

  except ValueError as e:
    print(f"Invalid input: {e}")

if __name__ == "__main__":
  main()
