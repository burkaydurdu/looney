(ns looney.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::nickname
 (fn [db]
   (-> db :user :nickname)))

(re-frame/reg-sub
 ::message
 (fn [db]
   (:message db)))

(re-frame/reg-sub
 ::walls
 (fn [db]
   (:walls db)))

(re-frame/reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))
