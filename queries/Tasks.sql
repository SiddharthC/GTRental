/**

Lists the general queries used thoughout the program for various tasks.

 Assume values preceded by '$' are managed by the application and are values entered by the user or stored in application.

**Query for a new user creation. */
INSERT INTO Login (username, user_passwd, user_type)
VALUES ($username, $user_passwd, $user_type);

 /**Query for inserting credit card information. */
INSERT INTO Credit_card (card_number, name_on_card, cvv, expiry_date, billing_address)
VALUES ($card_num, $name_on_card, $cvv, $expiry_date, $billing_address);

 /**Query for inserting member information. */
INSERT INTO Member (username, first_name, middle_name, last_name, email, address, phone, card_number, plan_type)
VALUES ($username, $first_name, $middle_name, $last_name, $email, $address, $phone, $card_number, $plan_type);

 /**Additional query to insert a driving plan. */
INSERT INTO driving_plan (plan_type, discount, montly_payment, annual_payment)
VALUES ($plan_type, $discount, $montly_payment, $annual_payment);

 /**Query for adding a new car. */
INSERT INTO Car (reg_no, aux_cable, transm_type, capacity, b_tooth, daily_rate, hourly_rate, color, car_type, model, loc_name)
VALUES($reg_no, $aux_cable, $transm_type, $capacity, $b_tooth, $daily_rate, $hourly_rate, $color, $car_type, $model, $loc_name);

 /**Query for reserving a car. */
INSERT INTO reservation (mem_username, car_no, loc_name, pickup_date, pickup_time, return_date, return_time, estimated_cost)
VALUES ($username, $carregno , $loc_name, convert($pickup_date, date), convert($pickup_time, time), convert($return_date, date), convert($return_time, time), $estimatecost);

 /**Side query to insert old return time. -(5) */
INSERT INTO extended_time (regID, ext_date, ext_time)
VALUES ($regID, convert($returndate, date),
                convert($returntime, time));

/**Query for inserting maintenance request for a car.
NOTE: $car_no is obtained from query (1)*/
INSERT INTO Maintenance_req (req_timestamp, car_no, req_username, description)
VALUES(NOW(), $car_no, $req_username, $description);

 /**Query for login check. */
SELECT 
    count(*)
FROM
    login
WHERE
    username = $uname
        and user_passwd = $user_passwd;

 /**Query for updating card information for member. */
UPDATE credit_card 
SET 
    card_number = $card_number,
    name_on_card = $cardname,
    cvv = $cvv,
    expiry_date = $expdate,
    billing_address = $biladdress
WHERE
    card_number IN (SELECT 
            card_number
        FROM
            member
        WHERE
            username = $uname);

 /**Query for updating personal information. */
UPDATE member 
SET 
    fisrt_name = $fname,
    middle_name = $mname,
    last_name = $lname,
    email = $email,
    address = $address,
    phone = $phone,
    plan_type = $ptype
WHERE
    username = $uname;

/**Query for view driving plans. */
SELECT 
    *
FROM
    driving_plan;

 /** Query for car availability search. */
(SELECT 
    c.reg_no as 'Car No',
    c.model as 'Model',
    c.car_type as 'Type',
    c.loc_name as 'Location',
    c.color as 'Color',
    c.hourly_rate as 'Hourly Rate',
    c.hourly_rate * (SELECT 
            (1 - d.discount)
        FROM
            driving_plan d
        WHERE
            d.plan_type = 'Frequent') as 'Freq Plan Rate',
    c.hourly_rate * (SELECT 
            (1 - d.discount)
        FROM
            driving_plan as d
        WHERE
            d.plan_type = 'Daily') as 'Daily Plan Rate',
    c.daily_rate as 'Daily Rate',
    c.capacity as 'Capacity',
    c.transm_type as 'Transmission',
    c.b_tooth as 'Bluetooth',
    c.aux_cable as 'Aux Cable',
    CASE sign(timestampdiff(day,
                $data_from_application,
                $data_from_application) - 1)
        WHEN
            - 1
        THEN
            (SELECT 
                    CASE
                            WHEN min(r.pickup_time) IS NULL THEN '-NA-'
                            ELSE min(r.pickup_time)
                        END
                FROM
                    reservation as r
                WHERE
                    r.pickup_date = cast($data_from_application AS date)
                        AND r.pickup_time > cast($data_from_application AS time)
                        AND r.cancel_flag = 0)
        ELSE '-NA-'
    END as 'Available Till',
    CASE sign(timestampdiff(day,
                $data_from_application,
                $data_from_application) - 1)
        WHEN
            - 1
        THEN
            c.hourly_rate * (SELECT 
                    (1 - d.discount)
                FROM
                    member m,
                    driving_plan as d
                WHERE
                    m.plan_type = d.plan_type
                        and m.username = $data_from_application) * (timestampdiff(HOUR,
                $data_from_application,
                $data_from_application))
        ELSE c.daily_rate * (timestampdiff(day,
            $data_from_application,
            $data_from_application))
    END as 'Estimated Cost'
FROM
    car as c
WHERE
    (c.car_type = $data_from_application
        OR c.model = $data_from_application)
        AND c.mflag = 0
        AND c.loc_name = $data_from_application
        AND c.reg_no NOT IN (SELECT 
            r.car_no
        FROM
            reservation as r
        WHERE
            r.return_flag = 0 AND r.cancel_flag = 0
                AND ((cast($data_from_application AS date) + cast($data_from_application AS time)) BETWEEN (r.pickup_date + r.pickup_time) AND (r.return_date + r.return_time)
                OR (cast($data_from_application AS date) + cast($data_from_application AS time)) BETWEEN (r.pickup_date + r.pickup_time) AND (r.return_date + r.return_time)))) UNION (SELECT 
    c.reg_no as 'Car No',
    c.model as 'model',
    c.car_type as 'type',
    c.loc_name as 'location',
    c.color as 'color',
    c.hourly_rate as 'Hourly Rate',
    c.hourly_rate * (SELECT 
            (1 - d.discount)
        FROM
            driving_plan d
        WHERE
            d.plan_type = 'Frequent') as 'Freq Plan Rate',
    c.hourly_rate * (SELECT 
            (1 - d.discount)
        FROM
            driving_plan as d
        WHERE
            d.plan_type = 'Daily') as 'Daily Plan Rate',
    c.daily_rate as 'Daily Rate',
    c.capacity as 'Capacity',
    c.transm_type as 'Transmission',
    c.b_tooth as 'Bluetooth',
    c.aux_cable as 'Aux Cable',
    CASE sign(timestampdiff(DAY,
                $data_from_application,
                $data_from_application) - 1)
        WHEN
            - 1
        THEN
            (SELECT 
                    CASE
                            WHEN min(r.pickup_time) IS NULL THEN '-NA-'
                            ELSE min(r.pickup_time)
                        END
                FROM
                    reservation as r
                WHERE
                    r.pickup_date = cast($data_from_application AS date)
                        AND r.pickup_time > cast($data_from_application AS time)
                        AND r.cancel_flag = 0)
        ELSE '-NA-'
    END as 'Available Till',
    CASE sign(timestampdiff(DAY,
                $data_from_application,
                $data_from_application) - 1)
        WHEN
            - 1
        THEN
            c.hourly_rate * (SELECT 
                    (1 - d.discount)
                FROM
                    member m,
                    driving_plan as d
                WHERE
                    m.plan_type = d.plan_type
                        and m.username = $data_from_application) * (timestampdiff(HOUR,
                $data_from_application,
                $data_from_application))
        ELSE c.daily_rate * (timestampdiff(DAY,
            $data_from_application,
            $data_from_application))
    END as 'Estimated Cost'
FROM
    car as c
WHERE
    (c.car_type = $data_from_application
        OR c.model = $data_from_application)
        AND c.mflag = 0
        AND c.loc_name <> $data_from_application
        AND c.reg_no NOT IN (SELECT 
            r.car_no
        FROM
            reservation as r
        WHERE
            r.return_flag = 0 AND r.cancel_flag = 0
                AND ((cast($data_from_application AS date) + cast($data_from_application AS time)) BETWEEN (r.pickup_date + r.pickup_time) AND (r.return_date + r.return_time)
                OR (cast($data_from_application AS date) + cast($data_from_application AS time)) BETWEEN (r.pickup_date + r.pickup_time) AND (r.return_date + r.return_time)))
order by c.loc_name DESC);

 /**Query for showing current reservations of member. -(3) */
SELECT 
    a.regID,
    a.pickup_date,
    a.return_date,
    a.pickup_time,
    a.return_time,
    b.model,
    a.loc_name,
    a.estimated_cost + a.late_fee AS total_cost
FROM
    reservation AS a,
    car AS b
WHERE
    a.car_no = b.reg_no
        AND a.mem_username = $uname
        AND cast(concat(a.return_date,' ', a.return_time) as datetime) > cast(NOW() as datetime)
        AND a.cancel_flag = 0;

 /**Query for updating return time for a reservation.
NOTE: values from (3) used.

**Side query to find if any user is affected by extension. -(4) */
SELECT 
    count(r.regID)
FROM
    reservation as r
WHERE
    r.return_flag = 0 AND r.cancel_flag = 0
        AND ((cast($newreturndate AS date) + cast($newreturntime AS time)) BETWEEN (r.pickup_date + r.pickup_time) AND (r.return_date + r.return_time))
        AND r.car_no = $car_no;

 /**Only executed in case when (4) returns 0 and is followed by (5). **/
UPDATE reservation as r 
SET 
    r.returndate = $newreturndate,
    r.returntime = $newreturntime
WHERE
    r.regID = $regID;

 /**Query for showing previous reservations of member. */
SELECT 
    a.pickup_date,
    a.return_date,
    a.pickup_time,
    a.return_time,
    b.model,
    a.loc_name,
    a.estimated_cost + a.late_fee AS total_cost,
    CASE
        WHEN a.late_by IS NULL THEN 'On Time'
        ELSE 'Late - ' || hour(a.late_by)
    END
FROM
    reservation AS a,
    car AS b
WHERE
    a.car_no = b.reg_no
        AND a.mem_username = $uname
        AND (cast(concat(a.return_date,' ', a.return_time) as datetime) < cast(NOW() as datetime) OR a.cancel_flag = 1);


 /**Query for car on a location. -(1) */
SELECT 
    c.reg_no, c.model
FROM
    car c
WHERE
    c.loc_name = $locname;

 /**Query for getting car data for car. */
SELECT 
    c.car_type, c.color, c.capacity, c.transm_type
FROM
    car c
WHERE
    c.reg_no = $carregno;

 /**Query for car location update */
UPDATE car 
SET 
    loc_name = $loc_name
WHERE
    reg_no = $regno;

 /**Query for rental change.  
When the member calls it means member is gonna be late. 
User gives an appx time he will be back by taking in a new arrival time. 
IF this time does not == another reservation pickup time, 
don't add late fee and update the arrival time. Else add in a late fee. 
If it does conflict, on the right screen, we need to select the conflicting member's information. 
The username, pickup time, original return time, email address, and phone number should be provided.


**Only executed in case (4) returns 0 followed by (5)*/
UPDATE reservation as r 
SET 
    r.late_by = r.late_by + datediff(hh,
            (cast($newreturndate AS date) + cast($newreturntime AS time)),
            (r.returndate + r.returntime)),
    r.returndate = $newreturndate,
    r.returntime = $newreturntime
WHERE
    r.regID = $regID;


/**Only executed in case (4) does not returns 0 followed by (5)*/
UPDATE reservation as r 
SET 
    r.late_fee = r.late_fee + 50 * (datediff(hh,
            (cast($newreturndate AS date) + cast($newreturntime AS time)),
            (r.returndate + r.returntime))),
    r.late_by = r.late_by + datediff(hh,
            (cast($newreturndate AS date) + cast($newreturntime AS time)),
            (r.returndate + r.returntime)),
    r.returndate = $newreturndate,
    r.returntime = $newreturntime
WHERE
    r.regID = $regID;

 /**Query showing affected user. -(2) */
SELECT 
    r.regID as regID,
    r.mem_username as name,
    r.pickup_date as pickup_date,
    r.pickup_time as pickup_time,
    r.return_date as return_date,
    r.return_time as return_time,
    m.email as email,
    m.phone as phone
FROM
    reservation r,
    member m
WHERE
    r.return_flag = 0 AND r.cancel_flag = 0
        AND r.mem_username = m.username
        AND m.username <> $data_from_application
        AND ((cast($data_from_application AS date) + cast($data_from_application AS time)) > (r.pickup_date + r.pickup_time));

 /**Query for cancelling reservation.
NOTE: $regID is obtained from query (2). */
UPDATE reservation r 
SET 
    r.cancel_flag = 1
WHERE
    r.regID = $regID;
	
/**Query for Revenue Generation Report. */
SELECT 
    C.reg_no AS Vehicle_Sno,
    C.car_type AS CarType,
    C.model AS ModelType,
    SUM(R.estimated_cost) AS Reservation_Revenue,
    SUM(R.late_fee) AS Late_Fees_Revenue
FROM
    car AS C,
    reservation AS R
WHERE
    C.reg_no = R.car_no AND R.cancel_flag = 0
        AND (R.pickup_date >= date_sub(curdate(), INTERVAL 3 MONTH))
GROUP BY Vehicle_Sno
ORDER BY carType;

 /**Query for Location Preference Report. Each query should return one row for each 
month, these three are unioned together to provide data for the
last three months. The addtime() function converts the date and time columns into
datetime.  The hour() function returns the number of hours in the time difference. */
(SELECT 
    monthname(curdate()) AS Month_Name,
    loc_name,
    count(R.pickup_date) AS No_of_Reservations,
    sum(hour(timediff(cast(concat(r.return_date, ' ', r.return_time) as datetime),
cast(concat(r.pickup_date, ' ', r.pickup_time) as datetime)))) as total_hours
FROM
    reservation AS R
WHERE
    monthname(R.pickup_date) = monthname(curdate())
        AND year(R.pickup_date) = year(curdate())
GROUP BY loc_name
order by No_of_Reservations desc
limit 1) 

UNION 

(SELECT 
    monthname(date_sub(curdate(), interval 1 MONTH)) AS Month_Name,
    loc_name,
    count(R.pickup_date) AS No_of_Reservations,
    sum(hour(timediff(cast(concat(r.return_date, ' ', r.return_time) as datetime),
cast(concat(r.pickup_date, ' ', r.pickup_time) as datetime)))) as total_hours

FROM
    reservation AS R
WHERE
    monthname(R.pickup_date) = monthname(date_sub(curdate(), interval 1 MONTH))
        AND year(R.pickup_date) = year(date_sub(curdate(), interval 1 MONTH))
GROUP BY loc_name
order by No_of_Reservations desc
limit 1) 

UNION 

(SELECT 
    monthname(date_sub(curdate(), interval 2 MONTH)) AS Month_Name,
    loc_name,
    count(R.pickup_date) AS No_of_Reservations,
    sum(hour(timediff(cast(concat(r.return_date, ' ', r.return_time) as datetime),
cast(concat(r.pickup_date, ' ', r.pickup_time) as datetime)))) as total_hours
FROM
    reservation AS R
WHERE
    monthname(R.pickup_date) = monthname(date_sub(curdate(), interval 2 MONTH))
        AND year(R.pickup_date) = year(date_sub(curdate(), interval 2 MONTH))
GROUP BY loc_name
order by No_of_Reservations desc
limit 1);

 /**Query for Frequent Users Report. */
SELECT 
    R.mem_username AS Username,
    M.plan_type AS Driving_Plan,
    (count(R.regID) / 3) AS Number_of_Reservations_per_month
FROM
    reservation AS R,
    member AS M
WHERE
    R.mem_username = M.username
        AND (R.pickup_date >= date_sub(curdate(), INTERVAL 3 MONTH))
GROUP BY R.mem_username
ORDER BY Number_of_Reservations_per_month desc
LIMIT 5;

 /**Query for Maintenance History Report.first a virtual table is 
created to count the number of problems for each car to order
the table in the final results */

SELECT 
    C.reg_no AS Car,
    M.req_timestamp AS Date_Time,
    M.req_username AS Employee,
    M.description AS Problem,
    S.Count as Number_of_Problems
FROM
    maintenance_req AS M
        LEFT OUTER JOIN
    Maintenance_History_Count as S ON M.car_no = S.car_no,
    car AS C
WHERE
    C.reg_no = M.car_no
ORDER BY S.count , C.reg_no;
