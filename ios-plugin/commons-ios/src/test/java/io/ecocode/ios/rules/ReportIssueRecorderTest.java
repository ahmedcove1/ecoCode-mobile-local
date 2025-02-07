/*
 * ecoCode iOS plugin - Help the earth, adopt this green plugin for your applications
 * Copyright © 2022 Green code Initiative (https://www.ecocode.io/)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.ecocode.ios.rules;

import io.ecocode.ios.checks.ReportIssue;
import io.ecocode.ios.checks.ReportIssueRecorder;
import org.junit.Test;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.sensor.internal.SensorContextTester;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ReportIssueRecorderTest {

    private static final String TEST_ROOT = "src/test/resources";
    private static final String TEST_FILENAME = "swift/main.swift";

    @Test
    public void recordIssues() {

        SensorContextTester context = SensorContextTester.create(new File(TEST_ROOT));
        DefaultInputFile testFile = new TestInputFileBuilder("", TEST_FILENAME)
                .setLanguage("swift")
                .setLines(10)
                .setOriginalLineEndOffsets(new int[10])
                .setOriginalLineStartOffsets(new int[10])
                .setModuleBaseDir(Paths.get(TEST_ROOT))
                .build();
        context.fileSystem().add(testFile);

        List<ReportIssue> issues = new ArrayList<>();
        issues.add(new ReportIssue("ruleId", "message", testFile.path().toString(), 3));

        ReportIssueRecorder recorder = new ReportIssueRecorder(context);
        recorder.recordIssues(issues, "TestRepo");

        assertThat(context.allIssues()).hasSize(1);

    }
}
