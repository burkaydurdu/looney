(ns looney.events
  (:require
   [re-frame.core :as re-frame]
   [looney.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   [day8.re-frame.http-fx]
   [ajax.core :as ajax]))

(goog-define api-url "http://localhost:3000")

(defn create-request-map
  ([type uri]
   (create-request-map type uri nil nil))
  ([type uri on-success]
   (create-request-map type uri on-success nil))
  ([type uri on-success on-fail]
   (cond-> {:method          type
            :uri             (str api-url uri)
            :format          (ajax/json-request-format)
            :response-format (ajax/json-response-format {:keywords? true})
            :on-success      (if (vector? on-success) on-success [on-success])
            :on-failure      (if (vector? on-fail) on-fail [on-fail])}
     (nil? on-success) (assoc :on-success [:no-http-on-ok])
     (nil? on-fail) (assoc :on-failure [:no-http-on-failure]))))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
   db/default-db))

(re-frame/reg-event-fx
  ::navigate
  (fn-traced [_ [_ handler]]
   {:navigate handler}))

(re-frame/reg-event-db
  ::set-data
  (fn-traced [db [_ path value]]
   (assoc-in db path value)))

(re-frame/reg-event-fx
 ::set-active-panel
 (fn-traced [{:keys [db]} [_ active-panel]]
   {:db (assoc db :active-panel active-panel)}))

(re-frame/reg-event-fx
  ::create-user
  (fn [{:keys [db]} _]
    {:http-xhrio (merge (create-request-map :post
                                            "/users"
                                            ::create-user-result-ok
                                            ::create-user-result-fail)
                        {:params {:nickname (-> db :user :nickname)}})}))

(re-frame/reg-event-fx
  ::create-user-result-ok
  (fn [{:keys [db]} [_ data]]
    {:db       (assoc db :user data)
     :dispatch [::navigate :wall]}))

(re-frame/reg-event-db
  ::create-user-result-fail
  (fn [db _]
    (assoc db :fail? true)))

(re-frame/reg-event-fx
  ::get-walls
  (fn [_ _]
    {:dispatch-later [{:ms 1000 :dispatch [::get-walls]}]
     :http-xhrio (create-request-map :get
                                     "/walls"
                                     ::get-walls-result-ok
                                     ::get-walls-result-fail)}))

(re-frame/reg-event-db
  ::get-walls-result-ok
  (fn [db [_ data]]
    (assoc db :walls data)))

(re-frame/reg-event-db
  ::get-walls-result-fail
  (fn [db _]
    (assoc db :fail? true)))

(re-frame/reg-event-fx
  ::create-message
  (fn [{:keys [db]} _]
    {:http-xhrio (merge (create-request-map :post
                                            "/walls"
                                            ::create-message-result-ok
                                            ::create-message-result-fail)
                        {:params {:user_id (-> db :user :id)
                                  :message (:message db)}})}))

(re-frame/reg-event-db
  ::create-message-result-ok
  (fn [db _]
    (assoc db :message "")))

(re-frame/reg-event-db
  ::create-user-result-fail
  (fn [db _]
    (assoc db :fail? true)))
