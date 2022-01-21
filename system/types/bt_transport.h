/*
 * Copyright 2020 The Android Open Source Project
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

#pragma once

#include <string>

#define BT_TRANSPORT_AUTO 0
#define BT_TRANSPORT_BR_EDR 1
#define BT_TRANSPORT_LE 2
typedef uint8_t tBT_TRANSPORT;

#define CASE_RETURN_TEXT(code) \
  case code:                   \
    return #code

inline std::string bt_transport_text(const tBT_TRANSPORT& transport) {
  switch (transport) {
    CASE_RETURN_TEXT(BT_TRANSPORT_AUTO);
    CASE_RETURN_TEXT(BT_TRANSPORT_BR_EDR);
    CASE_RETURN_TEXT(BT_TRANSPORT_LE);
    default:
      return std::string("UNKNOWN[") + std::to_string(transport) +
             std::string("]");
  }
}
#undef CASE_RETURN_TEXT