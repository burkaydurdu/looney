(ns looney.views
  (:require
   [re-frame.core :as re-frame]
   [looney.events :as events]
   [looney.routes :as routes]
   [reagent.core :as r]
   [looney.subs :as subs]))

;; home

(defn home-panel []
  (let [nickname @(re-frame/subscribe [::subs/nickname])]
    [:div.home
     [:input
      {:value nickname
       :on-change #(re-frame/dispatch [::events/set-data [:user :nickname] (-> % .-target .-value)])}]
     [:button
      {:on-click #(re-frame/dispatch [::events/create-user])}
      "Login"]]))


(defmethod routes/panels :home-panel [] [home-panel])

;; wall

(defn user-wall-card [wall]
  [:div.wall-card
    [:div.circle-avatar
     {:style {:background (str "#" (-> wall :user :avatar_color))}}]
    [:div.content
     [:span.nickname (-> wall :user :nickname)]
     [:div.message (str (:message wall))]]])

(defn wall-list []
  (r/create-class
    {:component-did-mount #(re-frame/dispatch [::events/get-walls])
     :reagent-render (fn []
                       [:div.message-box
                        (for [wall (sort-by :created_at > @(re-frame/subscribe [::subs/walls]))]
                          ^{:key (:id wall)}
                          [user-wall-card wall])])}))

(defn wall-panel []
  (let [message @(re-frame/subscribe [::subs/message])]
    [:div.wall
      [:div.action-box
       [:input
        {:value message
         :on-change #(re-frame/dispatch [::events/set-data [:message] (-> % .-target .-value)])}]
       [:button
        {:on-click #(re-frame/dispatch [::events/create-message])}
        "Send"]]
      [wall-list]]))

(defmethod routes/panels :wall-panel [] [wall-panel])

;; main

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [:div.app
     (routes/panels @active-panel)]))
