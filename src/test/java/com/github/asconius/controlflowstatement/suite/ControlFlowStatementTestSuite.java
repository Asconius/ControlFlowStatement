package com.github.asconius.controlflowstatement.suite;

import com.github.asconius.controlflowstatement.ForTest;
import com.github.asconius.controlflowstatement.IfTest;
import com.github.asconius.controlflowstatement.SwitchTest;
import com.github.asconius.controlflowstatement.WhileTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ForTest.class, IfTest.class, SwitchTest.class, WhileTest.class})
public class ControlFlowStatementTestSuite { }
