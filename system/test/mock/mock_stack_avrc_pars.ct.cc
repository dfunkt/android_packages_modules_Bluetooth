/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Generated mock file from original source file
 *   Functions generated:4
 */

#include <string.h>

#include <map>
#include <string>

#include "avrc_api.h"
#include "avrc_defs.h"
#include "osi/include/log.h"
#include "osi/include/osi.h"
#include "stack/avrc/avrc_int.h"
#include "test/common/mock_functions.h"

#ifndef UNUSED_ATTR
#define UNUSED_ATTR
#endif

tAVRC_STS AVRC_Ctrl_ParsResponse(tAVRC_MSG* p_msg, tAVRC_RESPONSE* p_result,
                                 uint8_t* p_buf, uint16_t* buf_len) {
  mock_function_count_map[__func__]++;
  return 0;
}
tAVRC_STS AVRC_ParsResponse(tAVRC_MSG* p_msg, tAVRC_RESPONSE* p_result,
                            UNUSED_ATTR uint8_t* p_buf,
                            UNUSED_ATTR uint16_t buf_len) {
  mock_function_count_map[__func__]++;
  return 0;
}
tAVRC_STS avrc_parse_notification_rsp(uint8_t* p_stream, uint16_t len,
                                      tAVRC_REG_NOTIF_RSP* p_rsp) {
  mock_function_count_map[__func__]++;
  return 0;
}
