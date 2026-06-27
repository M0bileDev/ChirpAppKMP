//
//  AppDelegate.swift
//  iosApp
//
//  Created by Damian Ogórek on 27/06/2026.
//

import ComposeApp
import FirebaseCore
import FirebaseMessaging
import Foundation
import UIKit
import UserNotifications

//Create similar implementation of android ChirpFirebaseMessagingService

// NSObject -> bridge Swift with ObjC
// UIApplicationDelegate -> handle lifecycle events (app's foreground or background)
// UNUserNotificationCenterDelegate -> how notifications are presented and how user interacts with them
// MessagingDelegate -> handles token changes
class AppDelegate: NSObject, UIApplicationDelegate,
    UNUserNotificationCenterDelegate, MessagingDelegate
{

    // called when should initialize Firebase Messaging on ios side
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication
            .LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        FirebaseApp.configure()

        UNUserNotificationCenter.current().delegate = self
        Messaging.messaging().delegate = self

        return true
    }
}
