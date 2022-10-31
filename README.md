# SRW Watchtower

**Note**: Watchtower is not yet daily driver ready. It's unstable and has build issues that are yet to be resolved. However, I'm happy to take testers for revisions!

SRW Watchtower is a fork of Twidere X, an open-source Twitter client for Android, that intends to improve upon the Twidere X codebase. It can be used as a replace for, and updated independently of, Twidere X with upstream updates coming as they're committed (see "Updates"). **I fully commit to providing regular updates, feature AND security, for the sake of users. (See "Twidere X Contributions")

Specific changes that will be made include:
- improved support for V2 API features (poll viewing and creation, as well as reply controls)
- more customization options
- more I haven't thought of yet but will declare later on in an update to this README.

### Updates
I've reset the versioning system to 1.0 since this is considered an entirely separate application from Twidere X. This means we're starting at 1.0.0.

For now, 1.0.0 includes the same feature set as Twidere X 1.6.y (when it was forked), and will be transformed into Watchtower *first* before additional feature changes are made.

### Twidere X Contributions
I plan to send some changes I make to the upstream to aid their efforts, mainly security and some minor features, while also maintaining watchtower as a separate project. I've also left the original README file for Twidere X in tact for your reference. It's below the line.

Feel free to submit code [here via merge/pull request](https://gitlab.com/sladewatkins/watchtower/-/merge_requests), *and/or* upstream as well! And thanks to the Twidere X folks for one of the best clients out there.

---

# Twidere X
[![Build Status](https://github.com/TwidereProject/TwidereX-Android/workflows/Android%20CI/badge.svg)](https://github.com/TwidereProject/TwidereX-Android/actions)
[![Crowdin](https://badges.crowdin.net/twidere-x/localized.svg)](https://crowdin.com/project/twidere-x)
[![Version](https://img.shields.io/github/v/release/TwidereProject/TwidereX-Android)](https://github.com/TwidereProject/TwidereX-Android/releases/latest)
[![Issues](https://img.shields.io/github/issues/TwidereProject/TwidereX-Android)](https://github.com/TwidereProject/TwidereX-Android/issues)
[![License](https://img.shields.io/github/license/TwidereProject/TwidereX-Android)](https://github.com/TwidereProject/TwidereX-Android/blob/develop/LICENSE)
![Activity](https://img.shields.io/github/commit-activity/m/TwidereProject/TwidereX-Android)
[![Contributors](https://img.shields.io/github/contributors/TwidereProject/TwidereX-Android)](https://github.com/TwidereProject/TwidereX-Android/graphs/contributors)

[<img src="https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png" alt="Get it on Google Play" height="80">](https://play.google.com/store/apps/details?id=com.twidere.twiderex)
[<img src="https://f-droid.org/badge/get-it-on.png" alt="Get it on F-Droid" height="80">](https://f-droid.org/en/packages/com.twidere.twiderex/)

Next generation of Twidere for Android 5.0+. **Still in early stage.**  

## Features

- Modern Material Design
- Dark mode
- Tweet with photos
- Multiple accounts support
- Free, open source, NO ads, forever!


## What's Happening

### What's new in 1.6.0 - July 2022 Update

- Experimental desktop version.
- Mute and block support.
- Gif insertion support
- Optimizing video play for timeline.
- Enable sensitive content blur for Twitter (by @enaix )
- Fix startup crash in Android 12
- Fix Clicking on the searchbar already with text (#304)
- Fix DMs tab is killed by block/deleted account (#384)
- Fix App crashes when viewing list members (#310)
- Add Share button for Tweet (#344)
- [Mastodon] Mastodon OAuth URL with spaces breaks when using DuckDuckGo as a browser (#326)

### What is being planned for 1.7.0 - July 2022 Update
For 1.7.0, we're focusing on bug fix and performance enhancement and there will be lots of internal code changes. You can check out our [milestone](https://github.com/TwidereProject/TwidereX-Android/milestone/6) for detail.

### Roadmap for 2.0 - Jun 2021 Update
For 2.0, we're considering these options, but it is still an early thought and might change over time.

- Desktop (Linux/Windows) support (by [compose-jb](https://github.com/JetBrains/compose-jb)).
- Tablet mode.
- Javascript extension support.
- View-Only mode (aka Anonymous Browse).

## Contributing

This project welcomes contributions of all types. Help spec'ing, design, documentation, finding bugs are ways everyone can help on top of coding features / bug fixes.

**Before you start work on a feature that you would like to contribute**, please read the [Contributor's Guide](CONTRIBUTING.md).

### ⚠ State of code ⚠

Twidere X is still in an early stage and will be periodically re-structuring/refactoring the code to make it easier to comprehend, navigate, build, test, and contribute to, so **DO expect significant changes to code layout on a regular basis**.

## License
```
                       Twidere X

     Copyright (C) TwidereProject and Contributors

Twidere X is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Twidere X is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Twidere X. If not, see <http://www.gnu.org/licenses/>.
```
