/**
 * Copyright (c) 2016-2017 Zerocracy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.zerocracy.stk.pm.comm

import com.zerocracy.pm.ClaimIn

assume.type('Notify').exact()

ClaimIn claim = new ClaimIn(xml)
String[] parts = claim.token().split(';')
if (parts[0] == 'slack') {
  claim.copy()
    .type('Notify in Slack')
    .postTo(project)
} else if (parts[0] == 'github') {
  claim.copy()
    .type('Notify in GitHub')
    .postTo(project)
} else if (parts[0] == 'job') {
  String[] job = parts[1].split(':')
  if (job[0] == 'gh') {
    String[] coords = job[1].split('#')
    claim.copy()
      .type('Notify in GitHub')
      .token("github;${coords[0]};${coords[1]}")
      .postTo(project)
  } else {
    throw new IllegalStateException(
      String.format(
        'I don\'t know how to notify job "%s"',
        parts[1]
      )
    )
  }
} else {
  throw new IllegalStateException(
    String.format(
      'I don\'t know how to notify "%s"',
      parts[0]
    )
  )
}
