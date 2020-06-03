#!/usr/bin/env python3

import subprocess, smtplib, ssl, sys, time
from email.message import EmailMessage
import re, config
import random, datetime
import os

"""
 RunTestSuite runs the unit tests in android studio a certain 
 number of times and emails the results using gradle commands. 

 Example of running this script:
 $ python RunTestSuite.py 2 clipsong@gmail.com
"""

def generateSummary(testResults, timeStart, timeEnd, i ):
    elapsedTime = timeEnd - timeStart
    outcome = "\nLoop " + str(i) + ": " + testResults +" Elapsed Time: " + str(round(elapsedTime, 2)) + "s\n"
    return outcome


def getTestResults(output):
    if "BUILD SUCCESSFUL" not in str(output):
        testResults = "\033[1;31;40m FAILED \x1b[0m"
        result = "Some Tests Failed "
    else:
        testResults = "\033[1;32;40m PASSED \x1b[0m"
        result = "All Tests Pass "
    return result


def runAndroidTest():
    workingDirectory = os.path.dirname(os.path.realpath(__file__)) 
    gradlewPath = os.path.join(workingDirectory, "gradlew")
    print(gradlewPath)
    subprocess.call(f"{gradlewPath} build -x lint", shell=True, cwd=workingDirectory)
    cmdExe = f"{gradlewPath} test"
    process = subprocess.Popen(cmdExe, stdout=subprocess.PIPE, shell=True)
    output = process.communicate()[0]
    output = str(output)
    return output

def createEmail(testResults, resultFormat, fails, total ):
    #version = random.randint(0, 500)
    today = datetime.datetime.now()
    email = "Subject: Trivial Trivia Test Summary - " + testResults  + today.strftime("%x") + " \n"
    email += total + resultFormat + fails
    return email


def formatLoopOutput(loopNum, outputOrig, verbose):
    countPass = 0
    countFail = 0
    output = outputOrig.replace('\\n', '\n')

    allP = re.findall("PASSED", output)
    for p in allP:
        countPass+=1
    allFails = re.findall("com.avinashdavid.trivialtrivia.*FAILED", output)
    fails = set(allFails)
    for f in allFails:
        countFail+=1

    totalUnitTests = countPass + countFail
    result = "  UNIT TESTS: Passed = " + str(countPass) + " Failed = " + str(countFail) + " - Loop " + str(loopNum) + "\n"
    if verbose:
        print(output)
        summary = ""
        allS = re.findall("RUNNING.*result: SUCCESS", output)
        for s in allS:
            summary += s + "\n"
        return summary +  result
    return (result, fails, countPass, totalUnitTests)


def runTestMultipleTimes( numLoop ):
    overallResult = ""
    avgTimeAndRuns = ""
    totalTime = 0
    for i in range(numLoop):
        timeStart = time.time()
        output = runAndroidTest()
        resultFormat, fails, countPass, totalUnitTests = formatLoopOutput( i, output , False )
        timeEnd = time.time()
        testResults = getTestResults( output )
        overallResult += "\n" + generateSummary(testResults, timeStart, timeEnd, i)
        overallResult += resultFormat
        avgTimeAndRuns = "\nIn Test Suite - Unit Tests (Passed {0} of {1})".format(countPass, totalUnitTests)
        totalTime += (timeEnd - timeStart)
    avgTime = totalTime / (i + 1)
    avgTimeAndRuns += "\nThe Entire Suite was Run {0} times - Avg Time {1:.2f}".format( str(i+1), avgTime )
    return [testResults, overallResult, fails, avgTimeAndRuns] 


def formatFailure( fails ):
    results = "\nFailures: \n"
    uniqueFail = set()
    for f in fails:
        if f not in uniqueFail:
            uniqueFail.add( f )
            results += "   "  + f + "\n"
    return results


if __name__ == "__main__":
    """
    Can take up to 2 Command Line Arguments,  the number of times to run the entire
    suite of tests from android and the email the report should be sent to.
    """

    if(len(sys.argv) == 3):
        numLoop = int(sys.argv[1])
        receiver_email = sys.argv[2]
        sendEmail = True
    elif(len(sys.argv) == 2):
        numLoop = int(sys.argv[1])
        sendEmail = False
    else:
        numLoop = 1
        sendEmail = False

    print( "\nTEST SUMMARY:\n")
    testResults, overallResult, fails, avgTimeAndRuns = runTestMultipleTimes(numLoop)
    uniqueFail = formatFailure( fails )
    print( avgTimeAndRuns )
    print( overallResult )
    print( uniqueFail )

    if sendEmail:
        port = 465  # For SSL
        smtp_server = "smtp.gmail.com"
        sender_email =  config.sender
        password = config.password

        email = createEmail(testResults, overallResult, uniqueFail, avgTimeAndRuns)
        context = ssl.create_default_context()

        with smtplib.SMTP_SSL(smtp_server, port, context=context) as server:
            server.login(sender_email, password)
            server.sendmail(sender_email, receiver_email, email)
            server.quit()
